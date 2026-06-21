package com.stocks.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", nullable = false, unique = true, length = 10)
    private String symbol;

    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Column(nullable = false, length = 50)
    private String sector;

    @Column(nullable = false, length = 20)
    private String exchange;

    protected Stock() {}

    public Stock(String symbol, String companyName, String sector, String exchange) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.sector = sector;
        this.exchange = exchange;
    }

    public Long getId()           { return id; }
    public String getSymbol()     { return symbol; }
    public String getCompanyName(){ return companyName; }
    public String getSector()     { return sector; }
    public String getExchange()   { return exchange; }
}
