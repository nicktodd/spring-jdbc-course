package com.bank;

import com.bank.config.AppProperties;
import com.bank.model.Account;
import com.bank.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Module 02 Demo: Introduction to SpringBoot
 * ===========================================
 * Compare this class with demos/01-introduction-to-spring/src/main/java/com/bank/Main.java.
 *
 * Key differences:
 *   - @SpringBootApplication replaces @Configuration + @ComponentScan
 *   - SpringApplication.run() replaces new AnnotationConfigApplicationContext(...)
 *   - The application context is created and managed by Boot, not us
 *   - CommandLineRunner is a functional interface; its run() method is called
 *     after the context is fully initialised - perfect for demo output
 *   - application.properties is loaded automatically - no manual config
 *
 * @SpringBootApplication is a composed annotation equivalent to:
 *   @SpringBootConfiguration    (marks this as the config class)
 *   @EnableAutoConfiguration    (activates auto-configuration)
 *   @ComponentScan              (scans this package and sub-packages)
 *
 * Run with:  mvn spring-boot:run
 */
@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    /**
     * CommandLineRunner is executed once after all beans are ready.
     * We inject AccountService and AppProperties directly as method parameters
     * (Spring passes them in automatically).
     */
    @Bean
    CommandLineRunner demo(AccountService accountService, AppProperties props) {
        return args -> {
            System.out.println("=== " + props.getServiceName() + " v" + props.getVersion() + " ===");
            System.out.println("Max accounts: " + props.getMaxAccounts());
            System.out.println();

            // These are the same service calls as Module 01.
            // SpringBoot changed the plumbing; the business API is identical.
            Account acc1 = accountService.openAccount(new Account(
                    null, "ACC-001", "Alice Smith", "SAVINGS",
                    new BigDecimal("15000.00"), LocalDate.of(2023, 6, 1)));

            Account acc2 = accountService.openAccount(new Account(
                    null, "ACC-002", "Bob Jones", "CURRENT",
                    new BigDecimal("3200.50"), LocalDate.of(2024, 1, 15)));

            Account acc3 = accountService.openAccount(new Account(
                    null, "ACC-003", "Carol White", "ISA",
                    new BigDecimal("20000.00"), LocalDate.of(2022, 4, 6)));

            System.out.println("Accounts opened:");
            accountService.getAllAccounts().forEach(a ->
                    System.out.printf("  [%d] %-8s  %-20s  %-8s  GBP %,.2f%n",
                            a.id(), a.accountNumber(), a.holderName(),
                            a.accountType(), a.balance()));

            System.out.println();
            System.out.println("Lookup by ID 2: " +
                    accountService.getAccountById(2L).map(Account::holderName).orElse("not found"));

            System.out.println();
            System.out.println("Attempting duplicate account number ACC-001...");
            try {
                accountService.openAccount(new Account(
                        null, "ACC-001", "Duplicate", "SAVINGS",
                        BigDecimal.ZERO, LocalDate.now()));
            } catch (IllegalArgumentException e) {
                System.out.println("Rejected: " + e.getMessage());
            }
        };
    }
}
