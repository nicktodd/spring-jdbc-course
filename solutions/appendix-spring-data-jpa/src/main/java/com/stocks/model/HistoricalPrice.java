package com.stocks.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "historical_price")
public class HistoricalPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock_id", nullable = false)
    private Long stockId;

    @Column(name = "price_date", nullable = false)
    private LocalDate priceDate;

    @Column(name = "open_price", nullable = false, precision = 15, scale = 4)
    private BigDecimal openPrice;

    @Column(name = "close_price", nullable = false, precision = 15, scale = 4)
    private BigDecimal closePrice;

    @Column(name = "high_price", nullable = false, precision = 15, scale = 4)
    private BigDecimal highPrice;

    @Column(name = "low_price", nullable = false, precision = 15, scale = 4)
    private BigDecimal lowPrice;

    @Column(nullable = false)
    private Long volume;

    protected HistoricalPrice() {}

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

    public Long getId()              { return id; }
    public Long getStockId()         { return stockId; }
    public LocalDate getPriceDate()  { return priceDate; }
    public BigDecimal getOpenPrice() { return openPrice; }
    public BigDecimal getClosePrice(){ return closePrice; }
    public BigDecimal getHighPrice() { return highPrice; }
    public BigDecimal getLowPrice()  { return lowPrice; }
    public Long getVolume()          { return volume; }
}
