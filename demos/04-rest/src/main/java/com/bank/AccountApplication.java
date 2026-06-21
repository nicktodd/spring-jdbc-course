package com.bank;

import com.bank.model.Account;
import com.bank.service.AccountService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Module 04 Demo - Account REST API.
 *
 * What changed from Module 03:
 *   - Added spring-boot-starter-web (Tomcat + Spring MVC + Jackson)
 *   - Added AccountController and TransactionController (@RestController)
 *   - Added GlobalExceptionHandler (@RestControllerAdvice)
 *   - Replaced CommandLineRunner with ApplicationRunner for seeding
 *     (same idea — runs after context starts — but avoids a startup print flood
 *     since the app now stays running as a server)
 *
 * What did NOT change:
 *   - Account, Transaction records
 *   - All repository classes and interfaces
 *   - AccountService interface and AccountServiceImpl
 *
 * Run:   mvn spring-boot:run
 * Then open a browser or use curl / Postman to call the API.
 *
 * Example curl commands (see README.md for the full list):
 *   curl http://localhost:8080/api/accounts
 *   curl -X POST http://localhost:8080/api/accounts \
 *        -H "Content-Type: application/json" \
 *        -d '{"accountNumber":"ACC-010","holderName":"Carol","accountType":"SAVINGS","balance":0,"openedDate":"2024-01-01"}'
 */
@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    // Seed two accounts on startup so the API has data to return immediately.
    // ApplicationRunner runs once after the Spring context is fully started.
    // Uses schema.sql's IF NOT EXISTS tables — safe to restart without duplicates
    // because we check for existing account numbers before inserting.
    @Bean
    ApplicationRunner seedData(AccountService service) {
        return args -> {
            if (service.getAllAccounts().isEmpty()) {
                Account alice = service.openAccount(new Account(
                        null, "ACC-001", "Alice Johnson", "CURRENT",
                        new BigDecimal("1000.00"), LocalDate.of(2024, 1, 15)));
                Account bob = service.openAccount(new Account(
                        null, "ACC-002", "Bob Smith", "SAVINGS",
                        new BigDecimal("500.00"), LocalDate.of(2024, 3, 20)));

                service.deposit(alice.id(), new BigDecimal("2500.00"), "Opening deposit");
                service.deposit(bob.id(), new BigDecimal("750.00"), "Opening deposit");

                System.out.println("Seed data loaded. API ready at http://localhost:8080/api/accounts");
            } else {
                System.out.println("Database already has data. API ready at http://localhost:8080/api/accounts");
            }
        };
    }
}
