package com.stocks;

import com.stocks.config.AppProperties;
import com.stocks.model.Stock;
import com.stocks.service.StockService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Module 02 Lab: Introduction to SpringBoot
 *
 * TODO:
 *   Step 1 - Add @SpringBootApplication to this class.
 *            This replaces the @Configuration + @ComponentScan from Module 01.
 *
 *   Step 2 - Implement the main() method:
 *            SpringApplication.run(StockApplication.class, args);
 *
 *   Step 3 - Implement the CommandLineRunner @Bean method.
 *            Inject StockService and AppProperties as parameters.
 *            In the runner:
 *              a. Print the service name and version from AppProperties.
 *              b. Add at least four stocks (use LSE-listed bank stocks).
 *              c. Print all stocks.
 *              d. Look up a stock by ID and print it.
 *              e. Demonstrate duplicate symbol rejection.
 *
 *   Step 4 - Add properties to application.properties (already started for you).
 *
 *   Step 5 - Delete AppConfig.java if you copied it from Module 01.
 *            @SpringBootApplication handles component scanning automatically.
 *
 * Run with:  mvn spring-boot:run
 */
public class StockApplication {   // TODO: add @SpringBootApplication

    public static void main(String[] args) {
        // TODO: replace with SpringApplication.run(...)
        System.out.println("TODO: start the SpringBoot application");
    }

    // TODO: add @Bean and complete this CommandLineRunner method
    CommandLineRunner demo(StockService stockService, AppProperties props) {
        return args -> {
            // TODO: implement demo output
        };
    }
}
