package com.bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Account domain record - unchanged from Module 01.
 * SpringBoot does not change how domain objects are written.
 */
public record Account(
        Long id,
        String accountNumber,
        String holderName,
        String accountType,
        BigDecimal balance,
        LocalDate openedDate
) {}
