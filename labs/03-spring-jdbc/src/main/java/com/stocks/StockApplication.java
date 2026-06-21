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
public class StockApplication {

    // TODO 1 and 2

    // TODO 3: Add CommandLineRunner @Bean
}
