package com.stocks.dto;

import com.stocks.model.Stock;

public record StockResponse(
        Long id,
        String symbol,
        String companyName,
        String sector,
        String exchange
) {
    public static StockResponse fromDomain(Stock s) {
        return new StockResponse(s.getId(), s.getSymbol(), s.getCompanyName(), s.getSector(), s.getExchange());
    }
}
