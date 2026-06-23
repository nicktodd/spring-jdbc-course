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
@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final HistoricalPriceRepository historicalPriceRepository;

    public StockServiceImpl(StockRepository stockRepository, HistoricalPriceRepository historicalPriceRepository) {
        this.stockRepository = stockRepository;
        this.historicalPriceRepository = historicalPriceRepository;
    }

    @Override
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    public Optional<Stock> getStockById(Long id) {
        return stockRepository.findById(id);
    }

    @Override
    public Stock addStock(Stock stock) {
        stockRepository.findBySymbol(stock.symbol()).ifPresent(existing -> {
            throw new IllegalArgumentException("Duplicate symbol: " + stock.symbol());
        });
        return stockRepository.save(stock);
    }

    @Override
    public HistoricalPrice addPrice(HistoricalPrice price) {
        return historicalPriceRepository.save(price);
    }

    @Override
    public List<HistoricalPrice> getPriceHistory(Long stockId) {
        return historicalPriceRepository.findByStockId(stockId);
    }
}
