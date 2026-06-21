package com.bank.service;

import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * AccountServiceImpl implements the business logic for account operations.
 *
 * KEY POINT - Constructor Injection:
 * The AccountRepository dependency is declared in the constructor, not instantiated
 * here. This class does not contain "new InMemoryAccountRepository()" anywhere.
 * Spring reads the constructor, sees that an AccountRepository is required, finds
 * its InMemoryAccountRepository bean, and passes it in automatically.
 *
 * This is Dependency Injection: the dependency is injected from outside rather than
 * created internally. The benefit is that this class can be tested by passing a mock
 * AccountRepository without needing Spring at all.
 *
 * @Service is a stereotype annotation that tells Spring to manage this class as a bean.
 * When Spring starts, it will:
 *   1. Detect this class via component scanning
 *   2. Call the constructor, injecting the AccountRepository bean
 *   3. Store the resulting instance in the application context
 */
@Service
public class AccountServiceImpl implements AccountService {

    // final ensures the dependency is set exactly once and never changed.
    private final AccountRepository accountRepository;

    // With a single constructor, @Autowired is optional in Spring 4.3+.
    // It is omitted here intentionally to keep the code clean.
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
        // Business rule: duplicate account numbers are not allowed.
        accountRepository.findByAccountNumber(account.accountNumber())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException(
                            "Account number already exists: " + account.accountNumber());
                });
        return accountRepository.save(account);
    }
}
