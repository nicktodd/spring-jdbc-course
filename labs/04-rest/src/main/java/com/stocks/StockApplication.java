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
public class StockApplication {

    // TODO 1 and 2

    // TODO 3: ApplicationRunner @Bean for seed data
}
