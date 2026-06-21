package com.bank.repository;

import com.bank.model.Account;

import java.util.List;
import java.util.Optional;

/** Unchanged from Module 01. */
public interface AccountRepository {
    List<Account> findAll();
    Optional<Account> findById(Long id);
    Optional<Account> findByAccountNumber(String accountNumber);
    Account save(Account account);
}
