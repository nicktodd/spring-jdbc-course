package com.stocks;

import com.stocks.model.HistoricalPrice;
import com.stocks.model.Stock;
import com.stocks.service.StockService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * TODO 1: Add @SpringBootApplication annotation.
 *
 * TODO 2: Add a main() method that calls SpringApplication.run().
 *
 * TODO 3: Add an ApplicationRunner @Bean that seeds the database if it is empty.
 *   - Add 2 stocks (e.g. HSBA/LSE and BP/LSE)
 *   - Add 3 days of historical prices for each
 *   - Print "API ready at http://localhost:8080/api/stocks"
 *
 * Run: mvn spring-boot:run
 * Test with:
 *   curl http://localhost:8080/api/stocks
 *   curl http://localhost:8080/api/stocks/1/prices
 */
@SpringBootApplication
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Bean
    ApplicationRunner seed(StockService stockService) {
        return args -> {
            if (!stockService.getAllStocks().isEmpty()) {
                System.out.println("API ready at http://localhost:8080/api/stocks");
                return;
            }
            Stock hsbc = stockService.addStock(new Stock(null, "HSBA.L", "HSBC Holdings", "Financials", "LSE"));
            Stock bp   = stockService.addStock(new Stock(null, "BP.L",   "BP PLC",        "Energy",     "LSE"));
            LocalDate today = LocalDate.now();
            for (int i = 2; i >= 0; i--) {
                stockService.addPrice(new HistoricalPrice(null, hsbc.id(), today.minusDays(i),
                        new BigDecimal("620.10"), new BigDecimal("625.50"), new BigDecimal("628.00"), new BigDecimal("618.00"), 12000000L));
                stockService.addPrice(new HistoricalPrice(null, bp.id(), today.minusDays(i),
                        new BigDecimal("410.20"), new BigDecimal("415.00"), new BigDecimal("417.50"), new BigDecimal("408.00"), 8000000L));
            }
            System.out.println("API ready at http://localhost:8080/api/stocks");
        };
    }
}
