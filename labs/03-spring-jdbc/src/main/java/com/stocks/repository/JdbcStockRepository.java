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

    private final JdbcTemplate jdbc;

    public JdbcStockRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Stock> rowMapper = (rs, rowNum) -> new Stock(
            rs.getLong("id"),
            rs.getString("symbol"),
            rs.getString("company_name"),
            rs.getString("sector"),
            rs.getString("exchange")
    );

    @Override
    public List<Stock> findAll() {
        return jdbc.query("SELECT * FROM stock ORDER BY symbol", rowMapper);
    }

    @Override
    public Optional<Stock> findById(Long id) {
        List<Stock> results = jdbc.query("SELECT * FROM stock WHERE id = ?", rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Optional<Stock> findBySymbol(String symbol) {
        List<Stock> results = jdbc.query("SELECT * FROM stock WHERE symbol = ?", rowMapper, symbol);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Override
    public Stock save(Stock stock) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO stock (symbol, company_name, sector, exchange) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, stock.symbol());
            ps.setString(2, stock.companyName());
            ps.setString(3, stock.sector());
            ps.setString(4, stock.exchange());
            return ps;
        }, keyHolder);
        return new Stock(keyHolder.getKey().longValue(), stock.symbol(), stock.companyName(), stock.sector(), stock.exchange());
    }
}
