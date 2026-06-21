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
public class StockApplication {
    // TODO 1-3
}
