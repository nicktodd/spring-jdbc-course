package com.stocks.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * HistoricalPrice JPA entity.
 *
 * TODO 1: Add @Entity and @Table(name = "historical_price").
 * TODO 2: Annotate id with @Id and @GeneratedValue(strategy = GenerationType.IDENTITY).
 * TODO 3: Annotate stockId with @Column(name = "stock_id", nullable = false).
 * TODO 4: Annotate priceDate with @Column(name = "price_date", nullable = false).
 * TODO 5: Annotate the price fields with @Column(nullable = false, precision = 15, scale = 4).
 * TODO 6: Annotate volume with @Column(nullable = false).
 * TODO 7: Add a protected no-arg constructor.
 */
public class HistoricalPrice {

    private Long id;
    private Long stockId;
    private LocalDate priceDate;
    private BigDecimal openPrice;
    private BigDecimal closePrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private Long volume;

    public HistoricalPrice(Long stockId, LocalDate priceDate,
                           BigDecimal openPrice, BigDecimal closePrice,
                           BigDecimal highPrice, BigDecimal lowPrice, Long volume) {
        this.stockId = stockId;
        this.priceDate = priceDate;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.volume = volume;
    }

    public Long getId()             { return id; }
    public Long getStockId()        { return stockId; }
    public LocalDate getPriceDate() { return priceDate; }
    public BigDecimal getOpenPrice(){ return openPrice; }
    public BigDecimal getClosePrice(){ return closePrice; }
    public BigDecimal getHighPrice(){ return highPrice; }
    public BigDecimal getLowPrice() { return lowPrice; }
    public Long getVolume()         { return volume; }
}
