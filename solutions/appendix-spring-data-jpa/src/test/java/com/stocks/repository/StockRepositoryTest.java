package com.stocks.repository;

import com.stocks.model.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Test
    @DisplayName("save() assigns a generated id")
    void save_assignsId() {
        Stock stock = new Stock("HSBA", "HSBC Holdings PLC", "Banking", "LSE");

        Stock saved = stockRepository.save(stock);

        assertThat(saved.getId()).isNotNull().isGreaterThan(0L);
    }

    @Test
    @DisplayName("findBySymbol returns the stock when it exists")
    void findBySymbol_found() {
        stockRepository.save(new Stock("BP", "BP PLC", "Energy", "LSE"));

        Optional<Stock> found = stockRepository.findBySymbol("BP");

        assertThat(found).isPresent();
        assertThat(found.get().getCompanyName()).isEqualTo("BP PLC");
    }

    @Test
    @DisplayName("findBySymbol returns empty when symbol does not exist")
    void findBySymbol_notFound() {
        assertThat(stockRepository.findBySymbol("UNKNOWN")).isEmpty();
    }

    @Test
    @DisplayName("findAll returns all saved stocks")
    void findAll_returnsAll() {
        stockRepository.save(new Stock("TSCO", "Tesco PLC", "Retail", "LSE"));
        stockRepository.save(new Stock("VOD",  "Vodafone Group", "Telecom", "LSE"));

        assertThat(stockRepository.findAll()).hasSize(2);
    }
}
