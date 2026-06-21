package com.bank.dto;

import com.bank.model.Account;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * AccountResponse is what the API returns to clients.
 *
 * It mirrors Account almost exactly here, but having a separate type means:
 *   - We can add computed fields (e.g. formattedBalance) without polluting the domain.
 *   - We can hide fields clients should not see (e.g. internal audit columns).
 *   - The domain model can change without breaking the API shape.
 *
 * The static factory method fromDomain() is the mapping logic.
 * Keeping it here means the controller stays clean and the domain stays ignorant of the API.
 */
public record AccountResponse(
        Long id,
        String accountNumber,
        String holderName,
        String accountType,
        BigDecimal balance,
        LocalDate openedDate
) {
    // Static factory: convert domain Account → AccountResponse.
    public static AccountResponse fromDomain(Account account) {
        return new AccountResponse(
                account.id(),
                account.accountNumber(),
                account.holderName(),
                account.accountType(),
                account.balance(),
                account.openedDate()
        );
    }
}
