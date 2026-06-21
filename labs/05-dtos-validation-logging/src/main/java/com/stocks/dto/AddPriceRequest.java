package com.stocks.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * TODO 1: Convert this to a Java record with fields:
 *   LocalDate priceDate, BigDecimal openPrice, BigDecimal closePrice,
 *   BigDecimal highPrice, BigDecimal lowPrice, long volume
 *
 * TODO 2: Add validation:
 *   priceDate:  @NotNull
 *   openPrice:  @NotNull, @DecimalMin("0.0001")
 *   closePrice: @NotNull, @DecimalMin("0.0001")
 *   highPrice:  @NotNull, @DecimalMin("0.0001")
 *   lowPrice:   @NotNull, @DecimalMin("0.0001")
 *   volume:     @Min(0)  (from jakarta.validation.constraints.Min)
 */
public class AddPriceRequest {
    // TODO 1 and 2
}
