package com.bank.repository;

import com.bank.model.Account;

import java.util.List;
import java.util.Optional;

/** Interface unchanged from Modules 01/02. The implementation changes to JDBC. */
public interface AccountRepository {
    List<Account> findAll();
    Optional<Account> findById(Long id);
    Optional<Account> findByAccountNumber(String accountNumber);
    Account save(Account account);
}
