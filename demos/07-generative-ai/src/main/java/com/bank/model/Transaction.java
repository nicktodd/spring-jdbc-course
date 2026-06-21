package com.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction represents a single debit or credit on an account.
 * New in Module 03 - the bank-schema.sql transaction table backs this record.
 */
public record Transaction(
        Long id,
        Long accountId,
        BigDecimal amount,
        String transactionType,  // CREDIT, DEBIT
        String description,
        LocalDateTime transactionDate
) {}
