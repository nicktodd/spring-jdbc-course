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

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account account = new Account(
                request.accountNumber(), request.holderName(),
                request.accountType(), request.balance(), request.openedDate());

        Account saved = accountService.openAccount(account);
        AccountResponse response = AccountResponse.fromDomain(saved);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }
}
