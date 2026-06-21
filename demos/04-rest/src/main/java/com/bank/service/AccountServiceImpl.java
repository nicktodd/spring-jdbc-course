package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * AccountServiceImpl in Module 03 adds deposit/withdraw/getTransactions.
 *
 * The openAccount method is unchanged from Module 02 - it still delegates to
 * AccountRepository.save(). The only difference is that save() now goes to MySQL
 * instead of an in-memory map, because we swapped the @Repository implementation.
 *
 * This is the Open/Closed Principle in practice: the service was open for extension
 * (new transaction methods) but closed for modification on existing behaviour.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    // Both repositories are auto-wired by Spring via constructor injection.
    public AccountServiceImpl(AccountRepository accountRepository,
                               TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
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
        // Reject duplicate account numbers - same business rule as Modules 01/02.
        accountRepository.findByAccountNumber(account.accountNumber())
                .ifPresent(a -> {
                    throw new IllegalArgumentException(
                            "Account number already exists: " + account.accountNumber());
                });
        return accountRepository.save(account);
    }

    @Override
    public Transaction deposit(Long accountId, BigDecimal amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));

        // Update the balance and persist the changed account.
        Account updated = new Account(account.id(), account.accountNumber(), account.holderName(),
                account.accountType(), account.balance().add(amount), account.openedDate());
        accountRepository.save(updated);

        // Record the transaction.
        Transaction txn = new Transaction(null, accountId, amount, "CREDIT", description, LocalDateTime.now());
        return transactionRepository.save(txn);
    }

    @Override
    public Transaction withdraw(Long accountId, BigDecimal amount, String description) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));

        if (account.balance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds in account: " + accountId);
        }

        Account updated = new Account(account.id(), account.accountNumber(), account.holderName(),
                account.accountType(), account.balance().subtract(amount), account.openedDate());
        accountRepository.save(updated);

        Transaction txn = new Transaction(null, accountId, amount, "DEBIT", description, LocalDateTime.now());
        return transactionRepository.save(txn);
    }

    @Override
    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }
}
