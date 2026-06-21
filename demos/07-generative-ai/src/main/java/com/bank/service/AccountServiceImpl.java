package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * AccountServiceImpl in Module 05 adds SLF4J logging.
 *
 * SLF4J (Simple Logging Facade for Java) is a logging API that is included with
 * spring-boot-starter. Spring Boot auto-configures Logback as the underlying
 * implementation — no extra setup needed.
 *
 * Key SLF4J points:
 *
 * 1. Logger is obtained via LoggerFactory.getLogger(ClassName.class).
 *    The class name appears in every log line so you know where it came from.
 *
 * 2. Log levels (from least to most severe):
 *    TRACE → DEBUG → INFO → WARN → ERROR
 *    Spring Boot defaults to INFO, so TRACE and DEBUG are suppressed unless configured.
 *
 * 3. Use parameterised logging: log.info("Opening account {}", accountNumber)
 *    NOT: log.info("Opening account " + accountNumber)
 *    The {} placeholder avoids string concatenation when the level is suppressed.
 *
 * 4. Log business events at INFO. Log technical details at DEBUG.
 *    Log exceptions at WARN or ERROR with the exception as the last argument.
 */
@Service
public class AccountServiceImpl implements AccountService {

    // Convention: one Logger per class, named after the class, private static final.
    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository,
                               TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        log.debug("Fetching all accounts");
        List<Account> accounts = accountRepository.findAll();
        log.debug("Found {} accounts", accounts.size());
        return accounts;
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        log.debug("Looking up account id={}", id);
        return accountRepository.findById(id);
    }

    @Override
    public Account openAccount(Account account) {
        log.info("Opening new account: number={}, holder={}", account.accountNumber(), account.holderName());

        accountRepository.findByAccountNumber(account.accountNumber()).ifPresent(a -> {
            log.warn("Duplicate account number rejected: {}", account.accountNumber());
            throw new IllegalArgumentException("Account number already exists: " + account.accountNumber());
        });

        Account saved = accountRepository.save(account);
        log.info("Account opened successfully: id={}, number={}", saved.id(), saved.accountNumber());
        return saved;
    }

    @Override
    public Transaction deposit(Long accountId, BigDecimal amount, String description) {
        log.info("Deposit: accountId={}, amount={}, desc={}", accountId, amount, description);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.warn("Deposit failed — account not found: id={}", accountId);
                    return new IllegalArgumentException("Account not found: " + accountId);
                });

        Account updated = new Account(account.id(), account.accountNumber(), account.holderName(),
                account.accountType(), account.balance().add(amount), account.openedDate());
        accountRepository.save(updated);

        Transaction txn = new Transaction(null, accountId, amount, "CREDIT", description, LocalDateTime.now());
        Transaction saved = transactionRepository.save(txn);
        log.info("Deposit complete: txnId={}, newBalance={}", saved.id(), updated.balance());
        return saved;
    }

    @Override
    public Transaction withdraw(Long accountId, BigDecimal amount, String description) {
        log.info("Withdrawal: accountId={}, amount={}, desc={}", accountId, amount, description);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.warn("Withdrawal failed — account not found: id={}", accountId);
                    return new IllegalArgumentException("Account not found: " + accountId);
                });

        if (account.balance().compareTo(amount) < 0) {
            log.warn("Withdrawal refused — insufficient funds: accountId={}, balance={}, requested={}",
                    accountId, account.balance(), amount);
            throw new IllegalArgumentException("Insufficient funds in account: " + accountId);
        }

        Account updated = new Account(account.id(), account.accountNumber(), account.holderName(),
                account.accountType(), account.balance().subtract(amount), account.openedDate());
        accountRepository.save(updated);

        Transaction txn = new Transaction(null, accountId, amount, "DEBIT", description, LocalDateTime.now());
        Transaction saved = transactionRepository.save(txn);
        log.info("Withdrawal complete: txnId={}, newBalance={}", saved.id(), updated.balance());
        return saved;
    }

    @Override
    public List<Transaction> getTransactions(Long accountId) {
        log.debug("Fetching transactions for accountId={}", accountId);
        return transactionRepository.findByAccountId(accountId);
    }
}
