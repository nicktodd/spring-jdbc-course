package com.bank.controller;

import com.bank.model.Account;
import com.bank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * AccountController exposes account operations as a JSON REST API.
 *
 * Key annotations:
 *
 * @RestController = @Controller + @ResponseBody
 *   Every method return value is serialised to JSON automatically by Jackson.
 *   No need to call ObjectMapper manually.
 *
 * @RequestMapping("/api/accounts")
 *   All methods in this class share this base URL.
 *
 * @GetMapping    → HTTP GET
 * @PostMapping   → HTTP POST
 * @PutMapping    → HTTP PUT
 * @DeleteMapping → HTTP DELETE
 *
 * @PathVariable  → extract a value from the URL path, e.g. /api/accounts/42
 * @RequestBody   → deserialise the JSON request body into a Java object
 *
 * ResponseEntity<T>
 *   Wraps the response with an explicit HTTP status code.
 *   ResponseEntity.ok(body)      → 200 OK
 *   ResponseEntity.created(uri)  → 201 Created + Location header
 *   ResponseEntity.notFound()    → 404 Not Found
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    // Constructor injection — Spring injects the AccountServiceImpl bean.
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // GET /api/accounts
    // Returns the full list of accounts. 200 OK even if the list is empty.
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // GET /api/accounts/{id}
    // Returns a single account, or 404 if not found.
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/accounts
    // Creates a new account. Returns 201 Created with a Location header
    // pointing to the new resource URL (e.g. /api/accounts/5).
    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account saved = accountService.openAccount(account);

        // Build the URI for the newly created resource.
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();

        return ResponseEntity.created(location).body(saved);
    }
}
