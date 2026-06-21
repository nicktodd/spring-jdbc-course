package com.bank.service;

import com.bank.model.Account;

import java.util.List;
import java.util.Optional;

/** Unchanged from Module 01. */
public interface AccountService {
    List<Account> getAllAccounts();
    Optional<Account> getAccountById(Long id);
    Account openAccount(Account account);
}
