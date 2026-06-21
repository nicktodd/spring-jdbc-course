package com.bank.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Full account statement response, including a list of transaction entries
 * and a computed closing balance.
 *
 * --- AI WORKFLOW NOTE ---
 * Prompt used:
 *
 *   "Create an AccountStatement DTO record in Java 21 for a bank statement.
 *    It should include: accountNumber, holderName, accountType, statementDate,
 *    openingBalance, closingBalance, and a list of StatementEntry."
 *
 * AI output was correct. One field was renamed: AI used "generatedDate",
 * changed to "statementDate" to match domain language used elsewhere.
 * ------------------------
 */
public record AccountStatement(
        String accountNumber,
        String holderName,
        String accountType,
        LocalDate statementDate,
        BigDecimal openingBalance,
        BigDecimal closingBalance,
        List<StatementEntry> entries
) {}
