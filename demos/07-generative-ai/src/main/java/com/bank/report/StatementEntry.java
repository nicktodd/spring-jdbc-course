package com.bank.report;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A single line in an account statement.
 *
 * --- AI WORKFLOW NOTE ---
 * Prompt used to generate this record:
 *
 *   "I have a Transaction record with fields: id, accountId, amount,
 *    transactionType (CREDIT/DEBIT), description, transactionDate.
 *    Create a StatementEntry DTO record for a bank statement that shows
 *    each transaction alongside a running balance. Use Java 21."
 *
 * AI output was correct and required no changes.
 * ------------------------
 */
public record StatementEntry(
        LocalDateTime date,
        String type,
        String description,
        BigDecimal amount,
        BigDecimal runningBalance
) {}
