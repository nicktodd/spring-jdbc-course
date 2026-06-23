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

    private final JdbcTemplate jdbc;

    public JdbcHistoricalPriceRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<HistoricalPrice> rowMapper = (rs, rowNum) -> new HistoricalPrice(
            rs.getLong("id"),
            rs.getLong("stock_id"),
            rs.getDate("price_date").toLocalDate(),
            rs.getBigDecimal("open_price"),
            rs.getBigDecimal("close_price"),
            rs.getBigDecimal("high_price"),
            rs.getBigDecimal("low_price"),
            rs.getLong("volume")
    );

    @Override
    public List<HistoricalPrice> findByStockId(Long stockId) {
        return jdbc.query(
                "SELECT * FROM historical_price WHERE stock_id = ? ORDER BY price_date DESC",
                rowMapper, stockId);
    }

    @Override
    public HistoricalPrice save(HistoricalPrice price) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO historical_price (stock_id, price_date, open_price, close_price, high_price, low_price, volume) VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, price.stockId());
            ps.setDate(2, Date.valueOf(price.priceDate()));
            ps.setBigDecimal(3, price.openPrice());
            ps.setBigDecimal(4, price.closePrice());
            ps.setBigDecimal(5, price.highPrice());
            ps.setBigDecimal(6, price.lowPrice());
            ps.setLong(7, price.volume());
            return ps;
        }, keyHolder);
        return new HistoricalPrice(keyHolder.getKey().longValue(), price.stockId(), price.priceDate(),
                price.openPrice(), price.closePrice(), price.highPrice(), price.lowPrice(), price.volume());
    }
}
