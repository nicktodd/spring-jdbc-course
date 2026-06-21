package com.stocks.repository;

import com.stocks.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for Stock.
 *
 * JpaRepository<Stock, Long> provides save(), findById(), findAll(), count(), etc.
 * Spring generates the implementation at runtime — no class body needed.
 *
 * TODO: Add a derived query method that finds a Stock by its symbol.
 *       The method name should start with findBy and follow the field name.
 *       Example: findBySymbol(String symbol) → SELECT * FROM stock WHERE symbol = ?
 */
public interface StockRepository extends JpaRepository<Stock, Long> {

    // TODO: Optional<Stock> findBySymbol(String symbol);
}
