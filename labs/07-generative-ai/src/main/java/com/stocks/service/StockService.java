package com.stocks.service;

import com.stocks.model.HistoricalPrice;
import com.stocks.model.Stock;
import java.util.List;
import java.util.Optional;

public interface StockService {
    List<Stock> getAllStocks();
    Optional<Stock> getStockById(Long id);
    Stock addStock(Stock stock);
    HistoricalPrice addPrice(HistoricalPrice price);
    List<HistoricalPrice> getPriceHistory(Long stockId);

    /**
     * Search for stocks whose company name contains the given query (case-insensitive).
     * TODO: Implement in StockServiceImpl — delegate to stockRepository.findByCompanyNameContaining().
     */
    List<Stock> searchByName(String query);
}
