package com.bank.controller;

import com.bank.model.Transaction;
import com.bank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * TransactionController exposes deposit, withdrawal, and history endpoints.
 *
 * Notice the base URL is nested under accounts: /api/accounts/{accountId}/...
 * This reflects the "transactions belong to an account" relationship in the URL
 * structure, which is a REST best practice for related resources.
 *
 * DepositRequest and WithdrawRequest are simple request body records.
 * Jackson deserialises the incoming JSON into them automatically.
 */
@RestController
@RequestMapping("/api/accounts/{accountId}")
public class TransactionController {

    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    // GET /api/accounts/{accountId}/transactions
    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@PathVariable Long accountId) {
        return accountService.getTransactions(accountId);
    }

    // POST /api/accounts/{accountId}/deposits
    // Body: { "amount": 500.00, "description": "Salary" }
    @PostMapping("/deposits")
    public ResponseEntity<Transaction> deposit(
            @PathVariable Long accountId,
            @RequestBody MoneyRequest request) {
        Transaction txn = accountService.deposit(accountId, request.amount(), request.description());
        return ResponseEntity.ok(txn);
    }

    // POST /api/accounts/{accountId}/withdrawals
    // Body: { "amount": 100.00, "description": "ATM" }
    @PostMapping("/withdrawals")
    public ResponseEntity<Transaction> withdraw(
            @PathVariable Long accountId,
            @RequestBody MoneyRequest request) {
        Transaction txn = accountService.withdraw(accountId, request.amount(), request.description());
        return ResponseEntity.ok(txn);
    }

    // Simple record used as the request body for deposit and withdrawal.
    // Jackson maps { "amount": 100.00, "description": "..." } to this automatically.
    public record MoneyRequest(BigDecimal amount, String description) {}
}
