package com.bank.controller;

import com.bank.dto.AccountResponse;
import com.bank.dto.CreateAccountRequest;
import com.bank.model.Account;
import com.bank.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * AccountController in Module 05 uses DTOs and @Valid.
 *
 * Changes from Module 04:
 *   - createAccount() accepts CreateAccountRequest DTO, not Account directly
 *   - @Valid on the @RequestBody triggers Bean Validation before the method runs
 *     If any constraint fails, Spring throws MethodArgumentNotValidException
 *     which GlobalExceptionHandler converts to a 400 with field-level error details
 *   - Return type is AccountResponse DTO, not Account domain object
 *   - A private toAccount() mapper converts DTO → domain before calling the service
 *   - A private toResponse() mapper converts domain → DTO after calling the service
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        return accountService.getAllAccounts().stream()
                .map(AccountResponse::fromDomain)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .map(AccountResponse::fromDomain)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // @Valid tells Spring to run Bean Validation on CreateAccountRequest before this method is called.
    // If any @NotBlank / @Pattern / @DecimalMin constraint fails, the method is never invoked.
    // GlobalExceptionHandler catches MethodArgumentNotValidException and returns a 400.
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account account = new Account(null,
                request.accountNumber(), request.holderName(),
                request.accountType(), request.balance(), request.openedDate());

        Account saved = accountService.openAccount(account);
        AccountResponse response = AccountResponse.fromDomain(saved);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
}
