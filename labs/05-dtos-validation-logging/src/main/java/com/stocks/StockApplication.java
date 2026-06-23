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
 * TODO 1: Add @SpringBootApplication.
 * TODO 2: Add main() calling SpringApplication.run().
 * TODO 3: Add ApplicationRunner @Bean that seeds stocks and prices if the database is empty.
 *
 * Run: mvn spring-boot:run
 * Test:
 *   curl http://localhost:8080/api/stocks
 *   # Validation failure (missing symbol):
 *   curl -X POST http://localhost:8080/api/stocks \
 *        -H "Content-Type: application/json" \
 *        -d '{"companyName":"Test","sector":"Tech","exchange":"LSE"}'
 */
@SpringBootApplication
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Bean
    ApplicationRunner seed(StockService stockService) {
        return args -> {
            if (!stockService.getAllStocks().isEmpty()) return;
            Stock hsbc = stockService.addStock(new Stock(null, "HSBA", "HSBC Holdings", "Financials", "LSE"));
            Stock bp   = stockService.addStock(new Stock(null, "BP",   "BP PLC",        "Energy",     "LSE"));
            LocalDate today = LocalDate.now();
            for (int i = 2; i >= 0; i--) {
                stockService.addPrice(new HistoricalPrice(null, hsbc.id(), today.minusDays(i),
                        new BigDecimal("620.10"), new BigDecimal("625.50"), new BigDecimal("628.00"), new BigDecimal("618.00"), 12000000L));
                stockService.addPrice(new HistoricalPrice(null, bp.id(), today.minusDays(i),
                        new BigDecimal("410.20"), new BigDecimal("415.00"), new BigDecimal("417.50"), new BigDecimal("408.00"), 8000000L));
            }
        };
    }
}
