package com.stocks.controller;

import com.stocks.model.Stock;
import com.stocks.service.StockService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StockSearchController.class)
class StockSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    private final Stock hsbc = new Stock(1L, "HSBA", "HSBC Holdings PLC", "Banking", "LSE");

    @Test
    @DisplayName("GET /api/stocks/search?q=HSBC returns 200 with matching results")
    void search_matchFound_returns200WithResults() throws Exception {
        when(stockService.searchByName("HSBC")).thenReturn(List.of(hsbc));

        mockMvc.perform(get("/api/stocks/search").param("q", "HSBC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].symbol").value("HSBA"))
                .andExpect(jsonPath("$[0].companyName").value("HSBC Holdings PLC"));
    }

    @Test
    @DisplayName("GET /api/stocks/search?q=nomatch returns 200 with empty array")
    void search_noMatch_returns200WithEmptyArray() throws Exception {
        when(stockService.searchByName("nomatch")).thenReturn(List.of());

        mockMvc.perform(get("/api/stocks/search").param("q", "nomatch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @DisplayName("GET /api/stocks/search with blank q returns 400")
    void search_blankQuery_returns400() throws Exception {
        mockMvc.perform(get("/api/stocks/search").param("q", "   "))
                .andExpect(status().isBadRequest());
    }
}
