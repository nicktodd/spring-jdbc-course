package com.bank.service;

import com.bank.model.Account;

import java.util.List;
import java.util.Optional;

/**
 * AccountService defines the business operations available on accounts.
 *
 * The service interface separates WHAT the application can do from HOW it does it.
 * Controllers and other callers depend on this interface. The implementation
 * (AccountServiceImpl) is where business logic lives.
 */
public interface AccountService {

    List<Account> getAllAccounts();

    Optional<Account> getAccountById(Long id);

    Account openAccount(Account account);
}
