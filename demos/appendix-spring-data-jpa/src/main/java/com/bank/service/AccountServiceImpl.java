package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * AccountServiceImpl with Spring Data JPA.
 *
 * @Transactional wraps every public method in a database transaction.
 * If any method throws an unchecked exception, the transaction rolls back
 * and no partial changes reach the database.
 *
 * With JPA inside a @Transactional method:
 *   - Entities retrieved via the repository are "managed" (tracked by Hibernate).
 *   - Calling account.setBalance(...) on a managed entity is enough to update it;
 *     Hibernate flushes the change automatically at commit (dirty checking).
 *   - We still call accountRepository.save() explicitly for clarity.
 *
 * GenAI review checklist — things AI sometimes misses:
 *   [ ] @Transactional on the service (not the controller or repository)
 *   [ ] @Transactional(readOnly=true) on query-only methods (small performance gain)
 *   [ ] Duplicate-check before save (business rule, not a DB constraint check)
 *   [ ] Correct exception type: IllegalArgumentException → 400, not RuntimeException → 500
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                               TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account openAccount(Account account) {
        log.info("Opening account: number={}, holder={}", account.getAccountNumber(), account.getHolderName());

        accountRepository.findByAccountNumber(account.getAccountNumber()).ifPresent(existing -> {
            log.warn("Duplicate account number: {}", account.getAccountNumber());
            throw new IllegalArgumentException("Account number already exists: " + account.getAccountNumber());
        });

        Account saved = accountRepository.save(account);
        log.info("Account opened: id={}", saved.getId());
        return saved;
    }

    @Override
    public Transaction deposit(Long accountId, BigDecimal amount, String description) {
        log.info("Deposit: accountId={}, amount={}", accountId, amount);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));

        // Hibernate tracks managed entities — setBalance() + save() persists the new balance.
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction txn = new Transaction(accountId, amount, "CREDIT", description, LocalDateTime.now());
        Transaction saved = transactionRepository.save(txn);
        log.info("Deposit complete: txnId={}, newBalance={}", saved.getId(), account.getBalance());
        return saved;
    }

    @Override
    public Transaction withdraw(Long accountId, BigDecimal amount, String description) {
        log.info("Withdrawal: accountId={}, amount={}", accountId, amount);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));

        if (account.getBalance().compareTo(amount) < 0) {
            log.warn("Insufficient funds: accountId={}, balance={}, requested={}",
                    accountId, account.getBalance(), amount);
            throw new IllegalArgumentException("Insufficient funds in account: " + accountId);
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction txn = new Transaction(accountId, amount, "DEBIT", description, LocalDateTime.now());
        Transaction saved = transactionRepository.save(txn);
        log.info("Withdrawal complete: txnId={}, newBalance={}", saved.getId(), account.getBalance());
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByAccountIdOrderByTransactionDateDesc(accountId);
    }
}
