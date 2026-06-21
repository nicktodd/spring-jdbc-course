package com.stocks.service;

import com.stocks.model.HistoricalPrice;
import com.stocks.model.Stock;
import com.stocks.repository.HistoricalPriceRepository;
import com.stocks.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * StockServiceImpl with Spring Data JPA.
 *
 * TODO 1: Add @Transactional to the class so every public method runs in a transaction.
 *         A transaction ensures that if something fails mid-method, no partial changes
 *         are committed to the database.
 *
 * TODO 2: For read-only methods (getAllStocks, getStockById, getPrices), override
 *         the class-level @Transactional with @Transactional(readOnly = true).
 *         This gives Hibernate a hint to skip dirty-checking — a small performance gain.
 *
 * TODO 3: In addStock(), call stockRepository.findBySymbol(stock.getSymbol()) and
 *         throw an IllegalArgumentException if it is already present.
 *
 * TODO 4: In addPrice(), call priceRepository.save(price) and return the saved entity.
 */
@Service
// TODO 1: @Transactional
public class StockServiceImpl implements StockService {

    private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

    private final StockRepository stockRepository;
    private final HistoricalPriceRepository priceRepository;

    public StockServiceImpl(StockRepository stockRepository,
                            HistoricalPriceRepository priceRepository) {
        this.stockRepository = stockRepository;
        this.priceRepository = priceRepository;
    }

    @Override
    // TODO 2: @Transactional(readOnly = true)
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    // TODO 2: @Transactional(readOnly = true)
    public Optional<Stock> getStockById(Long id) {
        return stockRepository.findById(id);
    }

    @Override
    public Stock addStock(Stock stock) {
        log.info("Adding stock: symbol={}", stock.getSymbol());
        // TODO 3: Check for duplicate symbol using findBySymbol(), throw if present.
        return stockRepository.save(stock);
    }

    @Override
    public HistoricalPrice addPrice(HistoricalPrice price) {
        log.info("Adding price for stockId={}, date={}", price.getStockId(), price.getPriceDate());
        // TODO 4: Save and return the price entity.
        return null;
    }

    @Override
    // TODO 2: @Transactional(readOnly = true)
    public List<HistoricalPrice> getPrices(Long stockId) {
        return priceRepository.findByStockIdOrderByPriceDateDesc(stockId);
    }
}
