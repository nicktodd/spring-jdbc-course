package com.bank.report;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Generates account statements by combining account data with transaction history.
 *
 * --- AI WORKFLOW NOTE ---
 * Prompt used to generate the first draft of this class:
 *
 *   "Write a Spring @Service class called ReportServiceImpl that implements ReportService.
 *    It should be injected with AccountRepository and TransactionRepository.
 *    generateStatement(Long accountId) should:
 *    1. Load the Account by id, throw IllegalArgumentException if not found.
 *    2. Load all transactions for the account, ordered chronologically (oldest first).
 *    3. Compute a running balance starting from zero, applying CREDIT as +amount
 *       and DEBIT as -amount.
 *    4. The opening balance is the account's current balance minus the net of all
 *       transactions (i.e. what the balance was before any transactions).
 *    5. Return an AccountStatement with statementDate = today.
 *    Use Java 21 records. The Transaction record has fields: id, accountId, amount,
 *    transactionType, description, transactionDate."
 *
 * Issues found during review and corrected:
 *   1. AI used Collections.sort() on the list — changed to stream sorted() to avoid
 *      mutating the list returned by the repository.
 *   2. AI computed openingBalance as balance.subtract(net) but got the sign wrong
 *      for DEBIT transactions. Fixed the net calculation (see comment below).
 *   3. AI did not import LocalDate — trivial fix but shows AI can miss imports.
 * ------------------------
 */
@Service
public class ReportServiceImpl implements ReportService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public ReportServiceImpl(AccountRepository accountRepository,
                             TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public AccountStatement generateStatement(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));

        // Retrieve transactions oldest-first so the running balance builds chronologically.
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId)
                .stream()
                .sorted((a, b) -> a.transactionDate().compareTo(b.transactionDate()))
                .toList();

        // Compute the net effect of all transactions.
        // CREDIT increases the balance; DEBIT decreases it.
        // AI review fix: original AI code subtracted for both — DEBIT must subtract.
        BigDecimal net = transactions.stream()
                .map(t -> "CREDIT".equals(t.transactionType())
                        ? t.amount()
                        : t.amount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Opening balance = current balance minus the net of all transactions.
        BigDecimal openingBalance = account.balance().subtract(net);

        // Build statement entries with a running balance.
        BigDecimal running = openingBalance;
        List<StatementEntry> entries = transactions.stream()
                .map(t -> {
                    BigDecimal signed = "CREDIT".equals(t.transactionType())
                            ? t.amount() : t.amount().negate();
                    return new StatementEntry(
                            t.transactionDate(),
                            t.transactionType(),
                            t.description(),
                            t.amount(),
                            openingBalance.add(transactions.subList(0, transactions.indexOf(t) + 1)
                                    .stream()
                                    .map(tx -> "CREDIT".equals(tx.transactionType())
                                            ? tx.amount() : tx.amount().negate())
                                    .reduce(BigDecimal.ZERO, BigDecimal::add))
                    );
                })
                .toList();

        return new AccountStatement(
                account.accountNumber(),
                account.holderName(),
                account.accountType(),
                LocalDate.now(),
                openingBalance,
                account.balance(),
                entries
        );
    }
}
