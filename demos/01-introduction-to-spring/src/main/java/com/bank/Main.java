package com.bank;

import com.bank.config.AppConfig;
import com.bank.model.Account;
import com.bank.service.AccountService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Module 01 Demo: Introduction to Spring
 * =======================================
 * This application demonstrates the core problem that Spring solves and how it solves it.
 *
 * Run with:  mvn compile exec:java -Dexec.mainClass="com.bank.Main"
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== PART 1: The problem - tight coupling ===");
        demonstrateTightCoupling();

        System.out.println();
        System.out.println("=== PART 2: The solution - Spring IoC ===");
        demonstrateSpringIoc();
    }

    /**
     * PART 1: Tight coupling without Spring.
     *
     * AccountServiceImpl is constructed manually and wired to its repository
     * by hand. In a real application with dozens of services this becomes
     * unmanageable. Every class must know exactly which concrete types to create.
     * Changing any implementation requires hunting down every construction site.
     */
    private static void demonstrateTightCoupling() {
        // The service instantiates its own repository - it is tightly coupled.
        // To swap the repository, you must change this code.
        var repository = new com.bank.repository.InMemoryAccountRepository();
        var service = new com.bank.service.AccountServiceImpl(repository);

        Account acc = service.openAccount(new Account(
                null, "ACC-001", "Alice Smith", "SAVINGS",
                new BigDecimal("5000.00"), LocalDate.of(2024, 1, 15)));

        System.out.println("Opened: " + acc);
        System.out.println("All accounts: " + service.getAllAccounts());
    }

    /**
     * PART 2: Inversion of Control with Spring.
     *
     * AnnotationConfigApplicationContext is the Spring IoC container.
     * We give it the configuration class; it does all the wiring automatically.
     *
     * We ask the container for an AccountService. Spring:
     *   1. Scanned com.bank and found AccountServiceImpl (@Service) and
     *      InMemoryAccountRepository (@Repository)
     *   2. Noticed AccountServiceImpl needs an AccountRepository in its constructor
     *   3. Created InMemoryAccountRepository first, then passed it into AccountServiceImpl
     *   4. Stored both as beans - we never called 'new' for either
     *
     * To use a different AccountRepository implementation, we would simply annotate
     * a different class with @Repository (and optionally @Primary). The service code
     * does not change at all.
     */
    private static void demonstrateSpringIoc() {
        // Create the Spring application context from our configuration class.
        // This triggers component scanning and bean creation.
        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {

            // Ask Spring for an AccountService bean. Spring finds AccountServiceImpl
            // and returns it already wired with its AccountRepository.
            AccountService service = context.getBean(AccountService.class);

            // Use the service exactly as in Part 1, but Spring handled all the wiring.
            Account acc = service.openAccount(new Account(
                    null, "ACC-001", "Alice Smith", "SAVINGS",
                    new BigDecimal("5000.00"), LocalDate.of(2024, 1, 15)));

            service.openAccount(new Account(
                    null, "ACC-002", "Bob Jones", "CURRENT",
                    new BigDecimal("2500.00"), LocalDate.of(2024, 3, 1)));

            System.out.println("Opened: " + acc);
            System.out.println("All accounts:");
            service.getAllAccounts().forEach(a ->
                    System.out.printf("  [%d] %s - %s (%.2f)%n",
                            a.id(), a.accountNumber(), a.holderName(), a.balance()));

        } // context.close() is called here automatically (try-with-resources)
    }
}
