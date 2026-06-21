package com.stocks.service;

import com.stocks.model.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {
    List<Stock> getAllStocks();
    Optional<Stock> getStockById(Long id);
    Stock addStock(Stock stock);
}
