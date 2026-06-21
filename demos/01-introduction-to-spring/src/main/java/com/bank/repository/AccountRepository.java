package com.bank.repository;

import com.bank.model.Account;

import java.util.List;
import java.util.Optional;

/**
 * AccountRepository defines the contract for account data access.
 *
 * Programming to an interface (rather than a concrete class) is a central
 * principle in Spring development. The service layer depends on this interface,
 * not on any specific implementation. This means we can swap InMemoryAccountRepository
 * for a JdbcAccountRepository (introduced in Module 03) without changing the service.
 *
 * This is the Dependency Inversion Principle in practice.
 */
public interface AccountRepository {

    List<Account> findAll();

    Optional<Account> findById(Long id);

    Optional<Account> findByAccountNumber(String accountNumber);

    Account save(Account account);
}
