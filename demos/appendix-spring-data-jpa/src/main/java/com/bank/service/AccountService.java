package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    Account openAccount(Account account);

    List<Account> getAllAccounts();

    Optional<Account> getAccountById(Long id);

    Transaction deposit(Long accountId, BigDecimal amount, String description);

    Transaction withdraw(Long accountId, BigDecimal amount, String description);

    List<Transaction> getTransactions(Long accountId);
}
