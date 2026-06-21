package com.stocks.service;

import com.stocks.model.Stock;
import com.stocks.repository.HistoricalPriceRepository;
import com.stocks.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StockServiceImpl.
 *
 * Setup:
 *   @ExtendWith(MockitoExtension.class) — activates @Mock and @InjectMocks
 *   @Mock StockRepository               — fake repository, no database
 *   @Mock HistoricalPriceRepository     — fake repository
 *   @InjectMocks StockServiceImpl       — the real service under test
 */
@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private HistoricalPriceRepository priceRepository;

    @InjectMocks
    private StockServiceImpl service;

    private Stock hsbc;

    @BeforeEach
    void setUp() {
        // TODO: initialise hsbc as new Stock(1L, "HSBA", "HSBC Holdings PLC", "Banking", "LSE")
    }

    @Test
    @DisplayName("addStock saves and returns the stock when symbol is unique")
    void addStock_uniqueSymbol_savesAndReturns() {
        // TODO 1: Configure stockRepository.findBySymbol("HSBA") to return Optional.empty()
        // TODO 2: Configure stockRepository.save(any()) to return hsbc
        // TODO 3: Call service.addStock(new Stock(null, "HSBA", "HSBC Holdings PLC", "Banking", "LSE"))
        // TODO 4: assertThat(result.id()).isEqualTo(1L)
        // TODO 5: verify(stockRepository).save(any(Stock.class))
    }

    @Test
    @DisplayName("addStock throws when symbol already exists")
    void addStock_duplicateSymbol_throwsIllegalArgument() {
        // TODO 1: Configure stockRepository.findBySymbol("HSBA") to return Optional.of(hsbc)
        // TODO 2: assertThatThrownBy(() -> service.addStock(...))
        //           .isInstanceOf(IllegalArgumentException.class)
        //           .hasMessageContaining("HSBA")
        // TODO 3: verify(stockRepository, never()).save(any())
    }

    @Test
    @DisplayName("getAllStocks delegates to the repository")
    void getAllStocks_delegatesToRepository() {
        // TODO 1: Configure stockRepository.findAll() to return List.of(hsbc)
        // TODO 2: Call service.getAllStocks()
        // TODO 3: assertThat(result).hasSize(1)
        // TODO 4: assertThat(result.getFirst().symbol()).isEqualTo("HSBA")
    }

    @Test
    @DisplayName("getStockById returns Optional.empty() when not found")
    void getStockById_notFound_returnsEmpty() {
        // TODO 1: Configure stockRepository.findById(99L) to return Optional.empty()
        // TODO 2: assertThat(service.getStockById(99L)).isEmpty()
    }
}
