package com.stocks;

import com.stocks.config.AppConfig;
import com.stocks.model.Stock;
import com.stocks.service.StockService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {

            StockService service = context.getBean(StockService.class);

            service.addStock(new Stock(null, "BARC.L", "Barclays PLC",       "Financials", "LSE"));
            service.addStock(new Stock(null, "LLOY.L", "Lloyds Banking Group","Financials", "LSE"));
            service.addStock(new Stock(null, "HSBA.L", "HSBC Holdings PLC",   "Financials", "LSE"));

            System.out.println("All stocks:");
            service.getAllStocks().forEach(s ->
                    System.out.printf("  [%d] %-8s %s (%s)%n",
                            s.id(), s.symbol(), s.companyName(), s.exchange()));

            System.out.println("\nFind by ID 2: " + service.getStockById(2L).orElse(null));

            System.out.println("\nAttempting duplicate symbol BARC.L...");
            try {
                service.addStock(new Stock(null, "BARC.L", "Duplicate", "Financials", "LSE"));
            } catch (IllegalArgumentException e) {
                System.out.println("Rejected as expected: " + e.getMessage());
            }
        }
    }
}
