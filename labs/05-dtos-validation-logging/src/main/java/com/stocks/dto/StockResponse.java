package com.stocks.dto;

import com.stocks.model.Stock;

/**
 * TODO: Convert this to a Java record with fields:
 *   Long id, String symbol, String companyName, String sector, String exchange
 *
 * Add a static factory method:
 *   public static StockResponse fromDomain(Stock stock) { ... }
 *
 * The fromDomain() method should copy all fields from the Stock domain record.
 */
public record StockResponse(Long id, String symbol, String companyName, String sector, String exchange) {
    public static StockResponse fromDomain(Stock stock) {
        return new StockResponse(stock.id(), stock.symbol(), stock.companyName(), stock.sector(), stock.exchange());
    }
}
