package com.bank.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Account domain object.
 *
 * A Java 21 record is used here: records are immutable value objects with
 * auto-generated constructor, getters, equals, hashCode, and toString.
 * They are ideal for domain objects that are not mutated after creation.
 */
public record Account(
        Long id,
        String accountNumber,
        String holderName,
        String accountType,   // CURRENT, SAVINGS, ISA
        BigDecimal balance,
        LocalDate openedDate
) {}
