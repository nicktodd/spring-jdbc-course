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

@Service
@Transactional
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
    @Transactional(readOnly = true)
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Stock> getStockById(Long id) {
        return stockRepository.findById(id);
    }

    @Override
    public Stock addStock(Stock stock) {
        log.info("Adding stock: symbol={}", stock.getSymbol());
        stockRepository.findBySymbol(stock.getSymbol()).ifPresent(existing -> {
            log.warn("Duplicate symbol rejected: {}", stock.getSymbol());
            throw new IllegalArgumentException("Symbol already exists: " + stock.getSymbol());
        });
        Stock saved = stockRepository.save(stock);
        log.info("Stock added: id={}", saved.getId());
        return saved;
    }

    @Override
    public HistoricalPrice addPrice(HistoricalPrice price) {
        log.info("Adding price: stockId={}, date={}", price.getStockId(), price.getPriceDate());
        return priceRepository.save(price);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoricalPrice> getPrices(Long stockId) {
        return priceRepository.findByStockIdOrderByPriceDateDesc(stockId);
    }
}
