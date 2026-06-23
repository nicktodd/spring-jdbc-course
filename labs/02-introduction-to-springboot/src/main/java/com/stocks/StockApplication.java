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
@SpringBootApplication
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(StockService stockService, AppProperties props) {
        return args -> {
            System.out.println("Service: " + props.getServiceName() + " v" + props.getVersion());
            System.out.println("Max stocks: " + props.getMaxStocks());

            stockService.addStock(new Stock(null, "BARC.L", "Barclays PLC", "Financials", "LSE"));
            stockService.addStock(new Stock(null, "LLOY.L", "Lloyds Banking Group", "Financials", "LSE"));
            stockService.addStock(new Stock(null, "HSBA.L", "HSBC Holdings", "Financials", "LSE"));
            stockService.addStock(new Stock(null, "NWG.L", "NatWest Group", "Financials", "LSE"));

            System.out.println("\nAll stocks:");
            stockService.getAllStocks().forEach(System.out::println);

            System.out.println("\nGet stock by ID 2:");
            stockService.getStockById(2L).ifPresent(System.out::println);

            System.out.println("\nAttempting duplicate symbol:");
            try {
                stockService.addStock(new Stock(null, "BARC.L", "Duplicate", "Financials", "LSE"));
            } catch (IllegalArgumentException e) {
                System.out.println("Caught: " + e.getMessage());
            }
        };
    }
}
