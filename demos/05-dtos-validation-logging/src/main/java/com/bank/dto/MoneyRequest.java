package com.bank.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * MoneyRequest is the validated request body for deposit and withdrawal endpoints.
 * Moved to the dto package from an inner class in Module 04's TransactionController.
 */
public record MoneyRequest(

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotBlank(message = "Description is required")
        String description
) {}
