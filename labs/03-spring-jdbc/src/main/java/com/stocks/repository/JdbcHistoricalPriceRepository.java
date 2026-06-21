package com.stocks.repository;

import com.stocks.model.HistoricalPrice;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * TODO 1: Add @Repository annotation.
 *
 * TODO 2: Declare a final JdbcTemplate field and constructor.
 *
 * TODO 3: Define a RowMapper<HistoricalPrice> lambda.
 *         Columns: id, stock_id, price_date, open_price, close_price,
 *                  high_price, low_price, volume
 *         Note: use rs.getDate("price_date").toLocalDate() for LocalDate.
 *
 * TODO 4: Implement findByStockId().
 *         SQL: SELECT * FROM historical_price WHERE stock_id = ? ORDER BY price_date DESC
 *
 * TODO 5: Implement save() with a KeyHolder.
 *         SQL: INSERT INTO historical_price
 *              (stock_id, price_date, open_price, close_price, high_price, low_price, volume)
 *              VALUES (?, ?, ?, ?, ?, ?, ?)
 *         Use Date.valueOf(price.priceDate()) to convert LocalDate to java.sql.Date.
 */
@Repository
public class JdbcHistoricalPriceRepository implements HistoricalPriceRepository {

    // TODO 2: Add JdbcTemplate field and constructor

    // TODO 3: Add RowMapper<HistoricalPrice> field

    @Override
    public List<HistoricalPrice> findByStockId(Long stockId) {
        // TODO 4
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public HistoricalPrice save(HistoricalPrice price) {
        // TODO 5
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
