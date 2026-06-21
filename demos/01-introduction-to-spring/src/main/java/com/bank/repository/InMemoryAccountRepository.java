package com.bank.repository;

import com.bank.model.Account;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * InMemoryAccountRepository stores accounts in a Map instead of a database.
 *
 * This implementation is used in Modules 01 and 02 so that we can demonstrate
 * Spring IoC and SpringBoot without needing a database. Module 03 replaces this
 * with JdbcAccountRepository, which writes to MySQL via JdbcTemplate.
 *
 * @Repository is a Spring stereotype annotation. It tells Spring to create an
 * instance of this class and manage it as a bean in the application context.
 * It is semantically equivalent to @Component but signals "this is a data access class".
 */
@Repository
public class InMemoryAccountRepository implements AccountRepository {

    // AtomicLong ensures thread-safe ID generation without synchronisation blocks.
    private final AtomicLong idSequence = new AtomicLong(1);
    private final Map<Long, Account> store = new ConcurrentHashMap<>();

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return store.values().stream()
                .filter(a -> a.accountNumber().equals(accountNumber))
                .findFirst();
    }

    @Override
    public Account save(Account account) {
        // If the account has no ID yet, assign the next sequence value.
        // Records are immutable, so we create a new instance with the assigned ID.
        Account toStore = (account.id() == null)
                ? new Account(idSequence.getAndIncrement(), account.accountNumber(),
                              account.holderName(), account.accountType(),
                              account.balance(), account.openedDate())
                : account;
        store.put(toStore.id(), toStore);
        return toStore;
    }
}
