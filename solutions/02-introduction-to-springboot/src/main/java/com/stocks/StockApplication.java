package com.stocks;

import com.stocks.config.AppProperties;
import com.stocks.model.Stock;
import com.stocks.service.StockService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(StockService stockService, AppProperties props) {
        return args -> {
            System.out.println("=== " + props.getServiceName() + " v" + props.getVersion() + " ===");
            System.out.println("Max stocks: " + props.getMaxStocks());
            System.out.println();

            stockService.addStock(new Stock(null, "BARC.L", "Barclays PLC",        "Financials", "LSE"));
            stockService.addStock(new Stock(null, "LLOY.L", "Lloyds Banking Group", "Financials", "LSE"));
            stockService.addStock(new Stock(null, "HSBA.L", "HSBC Holdings PLC",    "Financials", "LSE"));
            stockService.addStock(new Stock(null, "STAN.L", "Standard Chartered",   "Financials", "LSE"));

            System.out.println("All stocks:");
            stockService.getAllStocks().forEach(s ->
                    System.out.printf("  [%d] %-8s  %-30s  %s%n",
                            s.id(), s.symbol(), s.companyName(), s.exchange()));

            System.out.println();
            System.out.println("Find by ID 2: " +
                    stockService.getStockById(2L).map(Stock::companyName).orElse("not found"));

            System.out.println();
            System.out.println("Attempting duplicate symbol BARC.L...");
            try {
                stockService.addStock(new Stock(null, "BARC.L", "Duplicate", "Financials", "LSE"));
            } catch (IllegalArgumentException e) {
                System.out.println("Rejected: " + e.getMessage());
            }
        };
    }
}
