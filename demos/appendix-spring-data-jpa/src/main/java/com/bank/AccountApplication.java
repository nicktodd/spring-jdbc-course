package com.bank;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    /**
     * Seeds the database on startup if it is empty.
     * ApplicationRunner keeps the Tomcat server running after the lambda completes.
     */
    @Bean
    ApplicationRunner seedData(AccountRepository accounts, TransactionRepository transactions) {
        return args -> {
            if (accounts.count() > 0) {
                System.out.println("Database already seeded. Server running at http://localhost:8080");
                return;
            }

            // Create two accounts using JPA save().
            // Notice: no id in the constructor — the database assigns it via AUTO_INCREMENT.
            Account alice = accounts.save(new Account(
                    "ACC-001", "Alice Johnson", "CURRENT",
                    new BigDecimal("2500.00"), LocalDate.of(2023, 1, 15)));

            Account bob = accounts.save(new Account(
                    "ACC-002", "Bob Smith", "SAVINGS",
                    new BigDecimal("5000.00"), LocalDate.of(2023, 3, 20)));

            // Add transactions for Alice.
            transactions.save(new Transaction(alice.getId(), new BigDecimal("500.00"),
                    "CREDIT", "Salary payment", LocalDateTime.now().minusDays(5)));
            transactions.save(new Transaction(alice.getId(), new BigDecimal("75.50"),
                    "DEBIT", "Grocery shopping", LocalDateTime.now().minusDays(2)));
            transactions.save(new Transaction(alice.getId(), new BigDecimal("200.00"),
                    "DEBIT", "Rent contribution", LocalDateTime.now().minusDays(1)));

            // One transaction for Bob.
            transactions.save(new Transaction(bob.getId(), new BigDecimal("1000.00"),
                    "CREDIT", "Initial transfer", LocalDateTime.now().minusDays(3)));

            System.out.println("\n=== Module 07 Demo: Spring Data JPA ===");
            System.out.println("Seeded 2 accounts, 4 transactions.");
            System.out.println("\nTry these endpoints:");
            System.out.println("  GET  http://localhost:8080/api/accounts");
            System.out.println("  GET  http://localhost:8080/api/accounts/1");
            System.out.println("  GET  http://localhost:8080/api/accounts/1/transactions");
            System.out.println("  POST http://localhost:8080/api/accounts");
            System.out.println("  POST http://localhost:8080/api/accounts/1/deposits");
            System.out.println("  POST http://localhost:8080/api/accounts/1/withdrawals");
            System.out.println("\nServer running. Press Ctrl+C to stop.");
        };
    }
}
