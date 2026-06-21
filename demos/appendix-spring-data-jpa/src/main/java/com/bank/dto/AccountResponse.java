package com.bank.dto;

import com.bank.model.Account;
import java.math.BigDecimal;
import java.time.LocalDate;

public record AccountResponse(
        Long id,
        String accountNumber,
        String holderName,
        String accountType,
        BigDecimal balance,
        LocalDate openedDate
) {
    public static AccountResponse fromDomain(Account a) {
        return new AccountResponse(
                a.getId(), a.getAccountNumber(), a.getHolderName(),
                a.getAccountType(), a.getBalance(), a.getOpenedDate());
    }
}
