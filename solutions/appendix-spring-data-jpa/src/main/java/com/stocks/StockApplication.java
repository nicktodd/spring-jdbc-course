package com.stocks;

import com.stocks.model.HistoricalPrice;
import com.stocks.model.Stock;
import com.stocks.repository.HistoricalPriceRepository;
import com.stocks.repository.StockRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Bean
    ApplicationRunner seedData(StockRepository stocks, HistoricalPriceRepository prices) {
        return args -> {
            if (stocks.count() > 0) {
                System.out.println("Database already seeded. Server running at http://localhost:8080");
                return;
            }

            Stock hsbc = stocks.save(new Stock("HSBA", "HSBC Holdings PLC", "Banking", "LSE"));
            Stock bp   = stocks.save(new Stock("BP",   "BP PLC",            "Energy",  "LSE"));

            prices.save(new HistoricalPrice(hsbc.getId(), LocalDate.of(2024, 6, 3),
                    new BigDecimal("6.2100"), new BigDecimal("6.2800"),
                    new BigDecimal("6.3100"), new BigDecimal("6.1800"), 28_000_000L));
            prices.save(new HistoricalPrice(hsbc.getId(), LocalDate.of(2024, 6, 4),
                    new BigDecimal("6.2800"), new BigDecimal("6.3500"),
                    new BigDecimal("6.4000"), new BigDecimal("6.2600"), 31_500_000L));

            prices.save(new HistoricalPrice(bp.getId(), LocalDate.of(2024, 6, 3),
                    new BigDecimal("4.7200"), new BigDecimal("4.6900"),
                    new BigDecimal("4.7500"), new BigDecimal("4.6500"), 18_200_000L));

            System.out.println("\n=== Module 07 Solution: Spring Data JPA ===");
            System.out.println("Seeded 2 stocks, 3 prices.");
            System.out.println("\nTry these endpoints:");
            System.out.println("  GET  http://localhost:8080/api/stocks");
            System.out.println("  GET  http://localhost:8080/api/stocks/1");
            System.out.println("  GET  http://localhost:8080/api/stocks/1/prices");
            System.out.println("  POST http://localhost:8080/api/stocks");
            System.out.println("\nServer running. Press Ctrl+C to stop.");
        };
    }
}
