package com.stocks.dto;

/**
 * TODO 1: Convert this class to a Java record with fields:
 *   String symbol, String companyName, String sector, String exchange
 *
 * TODO 2: Add the following validation annotations (import from jakarta.validation.constraints):
 *   symbol:      @NotBlank  — required
 *                @Pattern(regexp = "[A-Z]{1,10}", message = "Symbol must be 1-10 uppercase letters")
 *   companyName: @NotBlank  — required
 *   sector:      @NotBlank  — required
 *   exchange:    @NotBlank  — required
 *                @Pattern(regexp = "LSE|NYSE|NASDAQ", message = "Exchange must be LSE, NYSE, or NASDAQ")
 */
public class CreateStockRequest {
    // TODO 1 and 2
}
