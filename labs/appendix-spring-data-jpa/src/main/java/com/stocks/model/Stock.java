package com.stocks.model;

import jakarta.persistence.*;

/**
 * Stock JPA entity.
 *
 * TODO 1: Add @Entity to make this class a JPA entity.
 * TODO 2: Add @Table(name = "stock") to map it to the stock table.
 *
 * The annotations below are provided as a guide — uncomment and complete them.
 */
// @Entity
// @Table(name = "stock")
public class Stock {

    // TODO 3: Add @Id to mark this as the primary key.
    // TODO 4: Add @GeneratedValue(strategy = GenerationType.IDENTITY) for AUTO_INCREMENT.
    private Long id;

    // TODO 5: Add @Column(name = "symbol", nullable = false, unique = true, length = 10)
    private String symbol;

    // TODO 6: Add @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    // TODO 7: Add @Column(nullable = false, length = 50)
    private String sector;

    // TODO 8: Add @Column(nullable = false, length = 20)
    private String exchange;

    // TODO 9: Add a protected no-arg constructor (required by JPA for proxy creation).
    // protected Stock() {}

    public Stock(String symbol, String companyName, String sector, String exchange) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.sector = sector;
        this.exchange = exchange;
    }

    public Long getId()          { return id; }
    public String getSymbol()    { return symbol; }
    public String getCompanyName(){ return companyName; }
    public String getSector()    { return sector; }
    public String getExchange()  { return exchange; }
}
