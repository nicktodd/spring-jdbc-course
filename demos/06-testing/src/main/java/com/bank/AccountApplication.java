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
 * Module 05 Demo — Controllers and Service Layers.
 *
 * What changed from Module 04:
 *   - Added spring-boot-starter-validation dependency
 *   - Added dto/ package: CreateAccountRequest, AccountResponse, MoneyRequest
 *   - AccountController now accepts DTOs, uses @Valid
 *   - AccountServiceImpl now logs via SLF4J
 *   - GlobalExceptionHandler now handles MethodArgumentNotValidException
 *
 * Run:  mvn spring-boot:run
 *
 * Test validation with curl (see README.md for full examples):
 *   # Missing accountNumber — should return 400 with field errors
 *   curl -X POST http://localhost:8080/api/accounts \
 *        -H "Content-Type: application/json" \
 *        -d '{"holderName":"Alice","accountType":"CURRENT","balance":0,"openedDate":"2024-01-01"}'
 *
 *   # Invalid accountType — should return 400
 *   curl -X POST http://localhost:8080/api/accounts \
 *        -H "Content-Type: application/json" \
 *        -d '{"accountNumber":"ACC-001","holderName":"Alice","accountType":"UNKNOWN","balance":0,"openedDate":"2024-01-01"}'
 */
@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

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
            }
            System.out.println("API ready at http://localhost:8080/api/accounts");
        };
    }
}
