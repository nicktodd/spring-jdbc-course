package com.bank.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * CreateAccountRequest is a DTO (Data Transfer Object) for the POST /api/accounts endpoint.
 *
 * Why use a DTO instead of accepting the Account domain record directly?
 *
 * 1. Validation: we annotate the DTO with constraints. The domain model stays clean.
 *    AccountServiceImpl never sees invalid data — @Valid rejects it at the controller.
 *
 * 2. API stability: the JSON field names in the DTO are part of the public contract.
 *    We can refactor the domain model without breaking API clients.
 *
 * 3. Security: we control exactly which fields clients can set.
 *    The 'id' field is not here — clients cannot supply their own id.
 *    The 'balance' field has a minimum — clients cannot create accounts with negative balance.
 *
 * Validation annotations (from Jakarta Bean Validation, included via spring-boot-starter-validation):
 *   @NotBlank  — non-null AND not whitespace-only
 *   @NotNull   — non-null (allows empty string)
 *   @Pattern   — must match the regex
 *   @DecimalMin — numeric minimum (inclusive=true by default)
 */
public record CreateAccountRequest(

        @NotBlank(message = "Account number is required")
        @Pattern(regexp = "ACC-\\d{3}", message = "Account number must match ACC-NNN (e.g. ACC-001)")
        String accountNumber,

        @NotBlank(message = "Holder name is required")
        String holderName,

        @NotBlank(message = "Account type is required")
        @Pattern(regexp = "CURRENT|SAVINGS|ISA", message = "Account type must be CURRENT, SAVINGS, or ISA")
        String accountType,

        @NotNull(message = "Opening balance is required")
        @DecimalMin(value = "0.00", message = "Opening balance cannot be negative")
        BigDecimal balance,

        @NotNull(message = "Opened date is required")
        LocalDate openedDate
) {}
