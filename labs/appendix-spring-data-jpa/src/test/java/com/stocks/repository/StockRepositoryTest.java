package com.stocks.repository;

import com.stocks.model.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @DataJpaTest slice test for StockRepository.
 *
 * Uses H2 in-memory database — MySQL is not required for these tests.
 * Hibernate creates the schema from the @Entity annotations (create-drop mode).
 * Each test runs in a transaction that rolls back at the end.
 *
 * TODO 1: Complete save_assignsId — save a new Stock and assert that getId() is not null.
 *
 * TODO 2: Complete findBySymbol_found — save a Stock with symbol "HSBA",
 *         then call findBySymbol("HSBA") and assert the result is present
 *         and getCompanyName() equals "HSBC Holdings PLC".
 *
 * TODO 3: Complete findBySymbol_notFound — call findBySymbol("UNKNOWN")
 *         and assert the result is empty.
 */
@DataJpaTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Test
    @DisplayName("save() assigns a generated id")
    void save_assignsId() {
        // TODO 1
    }

    @Test
    @DisplayName("findBySymbol returns the stock when it exists")
    void findBySymbol_found() {
        // TODO 2
    }

    @Test
    @DisplayName("findBySymbol returns empty when symbol does not exist")
    void findBySymbol_notFound() {
        // TODO 3
    }
}
