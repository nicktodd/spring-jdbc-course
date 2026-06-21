package com.stocks.repository;

import com.stocks.model.Stock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * TODO 1: This class needs a @Repository annotation.
 *
 * TODO 2: Declare a final JdbcTemplate field and add a constructor that
 *         accepts JdbcTemplate as a parameter (constructor injection).
 *
 * TODO 3: Define a RowMapper<Stock> field (as a lambda) that maps each
 *         ResultSet row to a Stock record. Column names to use:
 *           id, symbol, company_name, sector, exchange
 *
 * TODO 4: Implement findAll() using jdbc.query().
 *         SQL: SELECT * FROM stock ORDER BY symbol
 *
 * TODO 5: Implement findById() using jdbc.query() with an id parameter.
 *         Return Optional.empty() if the result list is empty.
 *         SQL: SELECT * FROM stock WHERE id = ?
 *
 * TODO 6: Implement findBySymbol() the same way as findById().
 *         SQL: SELECT * FROM stock WHERE symbol = ?
 *
 * TODO 7: Implement save() using a KeyHolder to capture the auto-generated id.
 *         SQL: INSERT INTO stock (symbol, company_name, sector, exchange) VALUES (?, ?, ?, ?)
 *         Return a new Stock record built with the generated id.
 *
 * Hint: Look at JdbcAccountRepository in the demo project for the pattern.
 */
@Repository
public class JdbcStockRepository implements StockRepository {

    // TODO 2: Add JdbcTemplate field and constructor

    // TODO 3: Add RowMapper<Stock> field

    @Override
    public List<Stock> findAll() {
        // TODO 4
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Optional<Stock> findById(Long id) {
        // TODO 5
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Optional<Stock> findBySymbol(String symbol) {
        // TODO 6
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Stock save(Stock stock) {
        // TODO 7
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
