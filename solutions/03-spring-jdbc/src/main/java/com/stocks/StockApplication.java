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

@SpringBootApplication
public class StockApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(StockService service) {
        return args -> {
            System.out.println("=".repeat(60));
            System.out.println("MODULE 03 SOLUTION - Spring JDBC");
            System.out.println("=".repeat(60));

            // Add stocks
            Stock hsbc = service.addStock(new Stock(null, "HSBA", "HSBC Holdings PLC", "Banking", "LSE"));
            Stock bp   = service.addStock(new Stock(null, "BP",   "BP PLC",            "Energy",  "LSE"));
            System.out.println("\nAdded stocks:");
            service.getAllStocks().forEach(s ->
                    System.out.printf("  %-6s %-30s %-10s %s%n",
                            s.symbol(), s.companyName(), s.sector(), s.exchange()));

            // Add price history for HSBC
            service.addPrice(new HistoricalPrice(null, hsbc.id(), LocalDate.of(2024, 6, 3),
                    bd("6.21"), bd("6.28"), bd("6.31"), bd("6.18"), 28_000_000L));
            service.addPrice(new HistoricalPrice(null, hsbc.id(), LocalDate.of(2024, 6, 4),
                    bd("6.28"), bd("6.19"), bd("6.30"), bd("6.15"), 32_000_000L));
            service.addPrice(new HistoricalPrice(null, hsbc.id(), LocalDate.of(2024, 6, 5),
                    bd("6.19"), bd("6.35"), bd("6.38"), bd("6.17"), 41_000_000L));

            // Add price history for BP
            service.addPrice(new HistoricalPrice(null, bp.id(), LocalDate.of(2024, 6, 3),
                    bd("4.52"), bd("4.48"), bd("4.55"), bd("4.45"), 19_000_000L));
            service.addPrice(new HistoricalPrice(null, bp.id(), LocalDate.of(2024, 6, 4),
                    bd("4.48"), bd("4.61"), bd("4.63"), bd("4.46"), 25_000_000L));
            service.addPrice(new HistoricalPrice(null, bp.id(), LocalDate.of(2024, 6, 5),
                    bd("4.61"), bd("4.57"), bd("4.65"), bd("4.54"), 22_000_000L));

            System.out.println("\nHSBC price history:");
            service.getPriceHistory(hsbc.id()).forEach(p ->
                    System.out.printf("  %s  open=%.4f  close=%.4f  vol=%,d%n",
                            p.priceDate(), p.openPrice(), p.closePrice(), p.volume()));

            // Duplicate rejection
            System.out.println("\nDuplicate symbol rejection:");
            try {
                service.addStock(new Stock(null, "HSBA", "Duplicate", "Banking", "LSE"));
            } catch (IllegalArgumentException e) {
                System.out.println("Rejected (expected): " + e.getMessage());
            }

            System.out.println("\n" + "=".repeat(60));
            System.out.println("Check MySQL:");
            System.out.println("  SELECT * FROM stock;");
            System.out.println("  SELECT * FROM historical_price ORDER BY stock_id, price_date;");
            System.out.println("=".repeat(60));
        };
    }

    private static BigDecimal bd(String val) {
        return new BigDecimal(val);
    }
}
