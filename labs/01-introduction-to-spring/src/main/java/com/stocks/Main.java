package com.stocks;

import com.stocks.config.AppConfig;
import com.stocks.model.Stock;
import com.stocks.service.StockService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        StockService stockService = context.getBean(StockService.class);

        stockService.addStock(new Stock(null, "BARC.L", "Barclays PLC", "Financials", "LSE"));
        stockService.addStock(new Stock(null, "LLOY.L", "Lloyds Banking Group", "Financials", "LSE"));
        stockService.addStock(new Stock(null, "HSBA.L", "HSBC Holdings", "Financials", "LSE"));

        System.out.println("All stocks:");
        stockService.getAllStocks().forEach(System.out::println);

        System.out.println("\nGet stock by ID 2:");
        stockService.getStockById(2L).ifPresent(System.out::println);

        System.out.println("\nAttempting duplicate symbol:");
        try {
            stockService.addStock(new Stock(null, "BARC.L", "Duplicate", "Financials", "LSE"));
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        context.close();
    }
}
