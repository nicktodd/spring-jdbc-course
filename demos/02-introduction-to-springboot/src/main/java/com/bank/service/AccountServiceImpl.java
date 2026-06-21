package com.bank.service;

import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * AccountServiceImpl - unchanged from Module 01.
 *
 * This class did not change when we switched to SpringBoot.
 * @Service, constructor injection, and the business logic are identical.
 * SpringBoot changed how the application STARTS and is CONFIGURED,
 * not how the beans inside it are written.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account openAccount(Account account) {
        accountRepository.findByAccountNumber(account.accountNumber())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            "Account number already exists: " + account.accountNumber());
                });
        return accountRepository.save(account);
    }
}
