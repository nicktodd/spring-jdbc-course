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
 * InMemoryAccountRepository - unchanged from Module 01.
 *
 * In Module 03 this class will be replaced by JdbcAccountRepository,
 * which persists accounts to MySQL via JdbcTemplate.
 * AccountServiceImpl will not need to change at all.
 */
@Repository
public class InMemoryAccountRepository implements AccountRepository {

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
        Account toStore = (account.id() == null)
                ? new Account(idSequence.getAndIncrement(), account.accountNumber(),
                              account.holderName(), account.accountType(),
                              account.balance(), account.openedDate())
                : account;
        store.put(toStore.id(), toStore);
        return toStore;
    }
}
