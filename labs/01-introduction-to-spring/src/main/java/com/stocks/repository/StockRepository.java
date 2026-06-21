package com.stocks.repository;

import com.stocks.model.Stock;

import java.util.List;
import java.util.Optional;

/**
 * StockRepository defines the data access contract for stocks.
 *
 * TODO: Define the following method signatures in this interface:
 *
 *   List<Stock> findAll()
 *       Returns all stocks held in the repository.
 *
 *   Optional<Stock> findById(Long id)
 *       Returns the stock with the given id, or Optional.empty() if not found.
 *
 *   Optional<Stock> findBySymbol(String symbol)
 *       Returns the stock with the given ticker symbol, or Optional.empty() if not found.
 *
 *   Stock save(Stock stock)
 *       Persists the stock and returns it. If the stock has no id, assign one.
 *
 * Programming to this interface (not to InMemoryStockRepository) means
 * that StockService will not need to change when we introduce a database in Module 03.
 */
public interface StockRepository {

    // TODO: add method signatures here

}
