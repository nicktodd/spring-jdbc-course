package com.bank.repository;

import com.bank.model.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @DataJpaTest loads only the JPA layer.
 *
 * What it does:
 *   - Starts an H2 in-memory database (no MySQL required for tests).
 *   - Asks Hibernate to create the schema from the @Entity classes (ddl-auto=create-drop).
 *   - Wires AccountRepository but nothing else (no controllers, no services).
 *   - Wraps each test in a transaction that rolls back at the end — tests are isolated.
 *
 * What it does NOT do:
 *   - Load the full Spring context (@SpringBootTest does that — much slower).
 *   - Connect to MySQL.
 *   - Run schema.sql (Hibernate creates the schema from entity annotations instead).
 *
 * This makes @DataJpaTest far faster than a full integration test while still
 * exercising the real repository and database interactions.
 */
@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("save() assigns a generated id")
    void save_assignsId() {
        Account account = new Account("ACC-001", "Alice Johnson", "CURRENT",
                new BigDecimal("1000.00"), LocalDate.of(2024, 1, 1));

        Account saved = accountRepository.save(account);

        assertThat(saved.getId()).isNotNull().isGreaterThan(0L);
    }

    @Test
    @DisplayName("findByAccountNumber returns the account when it exists")
    void findByAccountNumber_found() {
        accountRepository.save(new Account("ACC-002", "Bob Smith", "SAVINGS",
                new BigDecimal("500.00"), LocalDate.of(2024, 2, 1)));

        Optional<Account> found = accountRepository.findByAccountNumber("ACC-002");

        assertThat(found).isPresent();
        assertThat(found.get().getHolderName()).isEqualTo("Bob Smith");
    }

    @Test
    @DisplayName("findByAccountNumber returns empty when account does not exist")
    void findByAccountNumber_notFound() {
        Optional<Account> found = accountRepository.findByAccountNumber("ACC-999");

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("findAll returns all saved accounts")
    void findAll_returnsAllAccounts() {
        accountRepository.save(new Account("ACC-010", "Carol", "CURRENT",
                new BigDecimal("200.00"), LocalDate.now()));
        accountRepository.save(new Account("ACC-011", "Dave", "ISA",
                new BigDecimal("3000.00"), LocalDate.now()));

        assertThat(accountRepository.findAll()).hasSize(2);
    }
}
