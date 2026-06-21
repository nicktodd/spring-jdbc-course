package com.bank.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateAccountRequest(

        @NotBlank(message = "Account number is required")
        @Pattern(regexp = "ACC-\\d{3}", message = "Account number must match ACC-NNN")
        String accountNumber,

        @NotBlank(message = "Holder name is required")
        String holderName,

        @NotBlank(message = "Account type is required")
        @Pattern(regexp = "CURRENT|SAVINGS|ISA", message = "Account type must be CURRENT, SAVINGS or ISA")
        String accountType,

        @NotNull(message = "Opening balance is required")
        @DecimalMin(value = "0.00", message = "Opening balance cannot be negative")
        BigDecimal balance,

        @NotNull(message = "Opening date is required")
        LocalDate openedDate
) {}
