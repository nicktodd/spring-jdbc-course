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
        hsbc = new Stock(1L, "HSBA", "HSBC Holdings PLC", "Banking", "LSE");
    }

    @Test
    @DisplayName("addStock saves and returns the stock when symbol is unique")
    void addStock_uniqueSymbol_savesAndReturns() {
        when(stockRepository.findBySymbol("HSBA")).thenReturn(Optional.empty());
        when(stockRepository.save(any())).thenReturn(hsbc);
        Stock result = service.addStock(new Stock(null, "HSBA", "HSBC Holdings PLC", "Banking", "LSE"));
        assertThat(result.id()).isEqualTo(1L);
        verify(stockRepository).save(any(Stock.class));
    }

    @Test
    @DisplayName("addStock throws when symbol already exists")
    void addStock_duplicateSymbol_throwsIllegalArgument() {
        when(stockRepository.findBySymbol("HSBA")).thenReturn(Optional.of(hsbc));
        assertThatThrownBy(() -> service.addStock(new Stock(null, "HSBA", "HSBC Holdings PLC", "Banking", "LSE")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("HSBA");
        verify(stockRepository, never()).save(any());
    }

    @Test
    @DisplayName("getAllStocks delegates to the repository")
    void getAllStocks_delegatesToRepository() {
        when(stockRepository.findAll()).thenReturn(List.of(hsbc));
        List<Stock> result = service.getAllStocks();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().symbol()).isEqualTo("HSBA");
    }

    @Test
    @DisplayName("getStockById returns Optional.empty() when not found")
    void getStockById_notFound_returnsEmpty() {
        when(stockRepository.findById(99L)).thenReturn(Optional.empty());
        assertThat(service.getStockById(99L)).isEmpty();
    }
}
