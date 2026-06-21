package com.stocks.controller;

import com.stocks.model.Stock;
import com.stocks.service.StockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Slice test for StockController using @WebMvcTest.
 *
 * @WebMvcTest loads only the MVC layer — no database, no real service.
 * @MockBean replaces StockService with a Mockito mock.
 * MockMvc lets you fire HTTP requests and assert on the response.
 */
@WebMvcTest(StockController.class)
class StockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    private final Stock hsbc = new Stock(1L, "HSBA", "HSBC Holdings PLC", "Banking", "LSE");

    @Test
    @DisplayName("GET /api/stocks returns 200 and a JSON array")
    void getAll_returns200() throws Exception {
        // TODO 1: when(stockService.getAllStocks()).thenReturn(List.of(hsbc))
        // TODO 2: mockMvc.perform(get("/api/stocks"))
        //           .andExpect(status().isOk())
        //           .andExpect(jsonPath("$[0].symbol").value("HSBA"))
    }

    @Test
    @DisplayName("GET /api/stocks/{id} returns 200 when stock exists")
    void getById_found_returns200() throws Exception {
        // TODO 1: when(stockService.getStockById(1L)).thenReturn(Optional.of(hsbc))
        // TODO 2: mockMvc.perform(get("/api/stocks/1"))
        //           .andExpect(status().isOk())
        //           .andExpect(jsonPath("$.companyName").value("HSBC Holdings PLC"))
    }

    @Test
    @DisplayName("GET /api/stocks/{id} returns 404 when not found")
    void getById_notFound_returns404() throws Exception {
        // TODO 1: when(stockService.getStockById(99L)).thenReturn(Optional.empty())
        // TODO 2: mockMvc.perform(get("/api/stocks/99"))
        //           .andExpect(status().isNotFound())
    }

    @Test
    @DisplayName("POST /api/stocks returns 201 with valid body")
    void create_valid_returns201() throws Exception {
        // TODO 1: when(stockService.addStock(any())).thenReturn(hsbc)
        // TODO 2: String body = """
        //           { "symbol":"HSBA","companyName":"HSBC Holdings PLC",
        //             "sector":"Banking","exchange":"LSE" }
        //           """
        // TODO 3: mockMvc.perform(post("/api/stocks")
        //             .contentType(MediaType.APPLICATION_JSON).content(body))
        //           .andExpect(status().isCreated())
        //           .andExpect(header().exists("Location"))
    }

    @Test
    @DisplayName("POST /api/stocks returns 400 when symbol is missing")
    void create_missingSymbol_returns400() throws Exception {
        // TODO 1: String body = """
        //           { "companyName":"HSBC","sector":"Banking","exchange":"LSE" }
        //           """
        // TODO 2: mockMvc.perform(post("/api/stocks")
        //             .contentType(MediaType.APPLICATION_JSON).content(body))
        //           .andExpect(status().isBadRequest())
        //           .andExpect(jsonPath("$.fields.symbol").exists())
    }
}
