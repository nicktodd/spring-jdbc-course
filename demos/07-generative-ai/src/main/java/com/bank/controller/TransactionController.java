package com.bank.controller;

import com.bank.dto.MoneyRequest;
import com.bank.model.Transaction;
import com.bank.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts/{accountId}")
public class TransactionController {

    private final AccountService accountService;

    public TransactionController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@PathVariable Long accountId) {
        return accountService.getTransactions(accountId);
    }

    // @Valid validates MoneyRequest: amount > 0, description not blank.
    @PostMapping("/deposits")
    public ResponseEntity<Transaction> deposit(
            @PathVariable Long accountId,
            @Valid @RequestBody MoneyRequest request) {
        return ResponseEntity.ok(accountService.deposit(accountId, request.amount(), request.description()));
    }

    @PostMapping("/withdrawals")
    public ResponseEntity<Transaction> withdraw(
            @PathVariable Long accountId,
            @Valid @RequestBody MoneyRequest request) {
        return ResponseEntity.ok(accountService.withdraw(accountId, request.amount(), request.description()));
    }
}
