package com.stocks.repository;

import com.stocks.model.HistoricalPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for HistoricalPrice.
 *
 * TODO: Add a derived query method that returns all prices for a given stockId,
 *       ordered by priceDate descending.
 *       The method name encodes the WHERE clause and ORDER BY:
 *         findByStockIdOrderByPriceDateDesc(Long stockId)
 *         → SELECT * FROM historical_price WHERE stock_id = ? ORDER BY price_date DESC
 */
public interface HistoricalPriceRepository extends JpaRepository<HistoricalPrice, Long> {

    // TODO: List<HistoricalPrice> findByStockIdOrderByPriceDateDesc(Long stockId);
}
