package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * AccountService extended in Module 03 to include transaction operations.
 * Modules 01/02 only had account-level methods.
 */
public interface AccountService {
    List<Account> getAllAccounts();
    Optional<Account> getAccountById(Long id);
    Account openAccount(Account account);

    // New in Module 03 - deposit and withdraw update the balance and record a transaction.
    Transaction deposit(Long accountId, BigDecimal amount, String description);
    Transaction withdraw(Long accountId, BigDecimal amount, String description);
    List<Transaction> getTransactions(Long accountId);
}
