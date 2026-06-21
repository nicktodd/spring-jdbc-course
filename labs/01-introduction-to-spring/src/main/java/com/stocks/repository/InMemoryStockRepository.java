package com.stocks.repository;

import com.stocks.model.Stock;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * InMemoryStockRepository stores stocks in a Map.
 *
 * @Repository marks this as a Spring-managed data access bean.
 * Spring will create one instance of this class and inject it wherever
 * a StockRepository is required.
 *
 * TODO: Implement the StockRepository interface.
 *       Step 1: Add "implements StockRepository" to the class declaration.
 *       Step 2: Implement each method of the interface.
 *               Use the AtomicLong idSequence for ID generation
 *               and the ConcurrentHashMap store for storage.
 *       Step 3: For save(), if stock.getId() is null, create a new Stock
 *               with the next sequence ID and store that. Return the stored instance.
 */
@Repository
public class InMemoryStockRepository {   // TODO: add "implements StockRepository"

    private final AtomicLong idSequence = new AtomicLong(1);
    private final Map<Long, Stock> store = new ConcurrentHashMap<>();

    // TODO: implement findAll()

    // TODO: implement findById(Long id)

    // TODO: implement findBySymbol(String symbol)

    // TODO: implement save(Stock stock)
}
