package com.stocks.service;

import com.stocks.model.Stock;
import com.stocks.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * StockServiceImpl implements business logic for stock operations.
 *
 * TODO:
 *   Step 1: Add "implements StockService" to the class declaration.
 *   Step 2: Declare a private final StockRepository field.
 *   Step 3: Write a constructor that accepts StockRepository and assigns it to the field.
 *           Spring will inject the InMemoryStockRepository bean automatically.
 *   Step 4: Implement each method from the StockService interface.
 *           In addStock(), throw IllegalArgumentException if the symbol already exists.
 *
 * Why constructor injection?
 *   - The dependency is declared explicitly and cannot be null after construction.
 *   - The class can be tested without Spring by passing a mock StockRepository.
 *   - The field can be final, preventing accidental reassignment.
 */
@Service
public class StockServiceImpl {   // TODO: add "implements StockService"

    // TODO: declare private final StockRepository stockRepository

    // TODO: add constructor

    // TODO: implement getAllStocks()

    // TODO: implement getStockById(Long id)

    // TODO: implement addStock(Stock stock)
}
