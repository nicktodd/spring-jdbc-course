package com.stocks.service;

import com.stocks.model.HistoricalPrice;
import com.stocks.model.Stock;
import com.stocks.repository.HistoricalPriceRepository;
import com.stocks.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * TODO 1: Add @Service annotation.
 *
 * TODO 2: Declare final fields for StockRepository and HistoricalPriceRepository.
 *         Add a constructor that accepts both as parameters (constructor injection).
 *
 * TODO 3: Implement getAllStocks() - delegate to stockRepository.findAll().
 *
 * TODO 4: Implement getStockById() - delegate to stockRepository.findById().
 *
 * TODO 5: Implement addStock() with duplicate symbol check.
 *         - Call stockRepository.findBySymbol(stock.symbol())
 *         - If present, throw IllegalArgumentException("Duplicate symbol: " + stock.symbol())
 *         - Otherwise save and return.
 *
 * TODO 6: Implement addPrice() - delegate to historicalPriceRepository.save().
 *
 * TODO 7: Implement getPriceHistory() - delegate to historicalPriceRepository.findByStockId().
 */
public class StockServiceImpl implements StockService {

    // TODO 2: Add fields and constructor

    @Override
    public List<Stock> getAllStocks() {
        // TODO 3
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Optional<Stock> getStockById(Long id) {
        // TODO 4
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Stock addStock(Stock stock) {
        // TODO 5
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public HistoricalPrice addPrice(HistoricalPrice price) {
        // TODO 6
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public List<HistoricalPrice> getPriceHistory(Long stockId) {
        // TODO 7
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
