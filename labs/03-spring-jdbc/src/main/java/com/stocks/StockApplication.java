package com.stocks;

import com.stocks.model.HistoricalPrice;
import com.stocks.model.Stock;
import com.stocks.service.StockService;
import org.springframework.boot.CommandLineRunner;
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
 * TODO 3: Add a CommandLineRunner @Bean method that accepts StockService.
 *         Inside the runner:
 *           a. Add at least 2 stocks (e.g. HSBC on LSE, BP on LSE)
 *           b. Add at least 3 days of historical prices for each stock
 *           c. Print all stocks
 *           d. Print price history for the first stock
 *           e. Try to add a duplicate stock and catch the IllegalArgumentException
 *
 * Run: mvn spring-boot:run
 */
@SpringBootApplication
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(StockService stockService) {
        return args -> {
            Stock hsbc = stockService.addStock(new Stock(null, "HSBA.L", "HSBC Holdings", "Financials", "LSE"));
            Stock bp   = stockService.addStock(new Stock(null, "BP.L",   "BP PLC",        "Energy",     "LSE"));

            LocalDate today = LocalDate.now();
            for (int i = 2; i >= 0; i--) {
                stockService.addPrice(new HistoricalPrice(null, hsbc.id(), today.minusDays(i),
                        new BigDecimal("620.10"), new BigDecimal("625.50"), new BigDecimal("628.00"), new BigDecimal("618.00"), 12000000L));
                stockService.addPrice(new HistoricalPrice(null, bp.id(), today.minusDays(i),
                        new BigDecimal("410.20"), new BigDecimal("415.00"), new BigDecimal("417.50"), new BigDecimal("408.00"), 8000000L));
            }

            System.out.println("\nAll stocks:");
            stockService.getAllStocks().forEach(System.out::println);

            System.out.println("\nPrice history for HSBC:");
            stockService.getPriceHistory(hsbc.id()).forEach(p ->
                    System.out.println("  " + p.priceDate() + " close=" + p.closePrice()));

            System.out.println("\nAttempting duplicate:");
            try {
                stockService.addStock(new Stock(null, "HSBA.L", "Duplicate", "Financials", "LSE"));
            } catch (IllegalArgumentException e) {
                System.out.println("Caught: " + e.getMessage());
            }
        };
    }
}
