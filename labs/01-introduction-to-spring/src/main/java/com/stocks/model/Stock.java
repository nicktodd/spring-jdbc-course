package com.stocks.model;

/**
 * Stock represents a listed equity.
 *
 * TODO: Convert this class to a Java 21 record.
 *       A record is an immutable value object; it automatically generates
 *       the constructor, getters (using the field names directly), equals,
 *       hashCode, and toString.
 *
 *       The record should have these components:
 *         Long id
 *         String symbol        - e.g. "BARC.L"
 *         String companyName   - e.g. "Barclays PLC"
 *         String sector        - e.g. "Financials"
 *         String exchange      - e.g. "LSE"
 *
 *       Replace this class with:
 *         public record Stock(Long id, String symbol, ...) {}
 */
public class Stock {

    private Long id;
    private String symbol;
    private String companyName;
    private String sector;
    private String exchange;

    // TODO: replace this class body with a record declaration (see above)

    public Stock(Long id, String symbol, String companyName, String sector, String exchange) {
        this.id = id;
        this.symbol = symbol;
        this.companyName = companyName;
        this.sector = sector;
        this.exchange = exchange;
    }

    public Long getId() { return id; }
    public String getSymbol() { return symbol; }
    public String getCompanyName() { return companyName; }
    public String getSector() { return sector; }
    public String getExchange() { return exchange; }

    @Override
    public String toString() {
        return "Stock[id=" + id + ", symbol=" + symbol + ", company=" + companyName + "]";
    }
}
