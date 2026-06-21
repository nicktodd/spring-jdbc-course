package com.bank;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Module 03 Demo - Spring JDBC with MySQL.
 *
 * What changed from Module 02:
 *   - Added spring-boot-starter-data-jdbc and mysql-connector-j to pom.xml
 *   - Added DataSource properties in application.properties
 *   - Added schema.sql (bank-schema.sql) which Spring runs on startup
 *   - Replaced InMemoryAccountRepository with JdbcAccountRepository
 *   - Added Transaction model, TransactionRepository, JdbcTransactionRepository
 *   - Extended AccountService with deposit/withdraw/getTransactions
 *
 * What did NOT change:
 *   - Account record - identical to Modules 01/02
 *   - AccountRepository interface - identical to Modules 01/02
 *   - AccountServiceImpl.openAccount() - identical logic
 *
 * Run:  mvn spring-boot:run
 * Requires: MySQL running locally with bankdb database and appuser credentials.
 * See application.properties for setup SQL.
 */
@SpringBootApplication
public class AccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(AccountService service) {
        return args -> {

            System.out.println("=".repeat(60));
            System.out.println("MODULE 03 DEMO - Spring JDBC with MySQL");
            System.out.println("=".repeat(60));

            // ----------------------------------------------------------
            // PART 1: Open accounts
            // The interface is identical to Modules 01/02.
            // Under the hood, Account rows are persisted to MySQL.
            // ----------------------------------------------------------
            System.out.println("\n--- PART 1: Opening accounts ---");

            Account alice = service.openAccount(new Account(
                    null, "ACC-001", "Alice Johnson", "CURRENT",
                    new BigDecimal("1000.00"), LocalDate.of(2024, 1, 15)));

            Account bob = service.openAccount(new Account(
                    null, "ACC-002", "Bob Smith", "SAVINGS",
                    new BigDecimal("500.00"), LocalDate.of(2024, 3, 20)));

            System.out.println("Opened: " + alice);
            System.out.println("Opened: " + bob);

            // ----------------------------------------------------------
            // PART 2: Deposit and withdraw
            // New in Module 03 - these persist Transaction rows to MySQL
            // and update the account balance.
            // ----------------------------------------------------------
            System.out.println("\n--- PART 2: Deposits and withdrawals ---");

            Transaction t1 = service.deposit(alice.id(), new BigDecimal("2500.00"), "Salary payment");
            System.out.println("Deposit: " + t1);

            Transaction t2 = service.withdraw(alice.id(), new BigDecimal("120.00"), "Utility bill");
            System.out.println("Withdrawal: " + t2);

            Transaction t3 = service.deposit(bob.id(), new BigDecimal("750.00"), "Freelance income");
            System.out.println("Deposit: " + t3);

            // ----------------------------------------------------------
            // PART 3: Read back from the database
            // These are SELECT queries - data comes from MySQL, not memory.
            // ----------------------------------------------------------
            System.out.println("\n--- PART 3: Current balances from MySQL ---");
            service.getAllAccounts().forEach(a ->
                    System.out.printf("  %-10s %-20s %s %10.2f%n",
                            a.accountNumber(), a.holderName(), a.accountType(), a.balance()));

            // ----------------------------------------------------------
            // PART 4: Transaction history for Alice
            // JdbcTransactionRepository.findByAccountId() queries by FK.
            // ----------------------------------------------------------
            System.out.println("\n--- PART 4: Transaction history for Alice ---");
            service.getTransactions(alice.id()).forEach(t ->
                    System.out.printf("  %-6s %8.2f  %s%n",
                            t.transactionType(), t.amount(), t.description()));

            // ----------------------------------------------------------
            // PART 5: Demonstrate duplicate account rejection
            // Business rule in AccountServiceImpl - unchanged from Module 02.
            // ----------------------------------------------------------
            System.out.println("\n--- PART 5: Duplicate account rejection ---");
            try {
                service.openAccount(new Account(
                        null, "ACC-001", "Alice Duplicate", "CURRENT",
                        BigDecimal.ZERO, LocalDate.now()));
            } catch (IllegalArgumentException e) {
                System.out.println("Rejected (expected): " + e.getMessage());
            }

            // ----------------------------------------------------------
            // PART 6: Demonstrate insufficient funds rejection
            // ----------------------------------------------------------
            System.out.println("\n--- PART 6: Insufficient funds rejection ---");
            try {
                service.withdraw(bob.id(), new BigDecimal("99999.00"), "Attempted overdraft");
            } catch (IllegalArgumentException e) {
                System.out.println("Rejected (expected): " + e.getMessage());
            }

            System.out.println("\n" + "=".repeat(60));
            System.out.println("Demo complete. Check MySQL to see persisted data:");
            System.out.println("  SELECT * FROM account;");
            System.out.println("  SELECT * FROM transaction;");
            System.out.println("=".repeat(60));
        };
    }
}
