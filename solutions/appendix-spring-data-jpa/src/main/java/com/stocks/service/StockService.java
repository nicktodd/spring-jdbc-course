package com.stocks.service;

import com.stocks.model.HistoricalPrice;
import com.stocks.model.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {

    Stock addStock(Stock stock);

    List<Stock> getAllStocks();

    Optional<Stock> getStockById(Long id);

    HistoricalPrice addPrice(HistoricalPrice price);

    List<HistoricalPrice> getPrices(Long stockId);
}
