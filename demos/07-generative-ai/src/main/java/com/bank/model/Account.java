package com.bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Unchanged from Modules 01 and 02. */
public record Account(
        Long id,
        String accountNumber,
        String holderName,
        String accountType,
        BigDecimal balance,
        LocalDate openedDate
) {}
