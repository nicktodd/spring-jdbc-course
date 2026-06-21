package com.stocks.controller;

import com.stocks.model.Stock;
import com.stocks.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for StockSearchController — to be completed using an AI tool.
 *
 * Use this prompt with your AI tool to generate the test bodies, then review
 * and correct the output before running:
 *
 *   "Write @WebMvcTest tests for StockSearchController using MockMvc and @MockBean StockService.
 *    Test 1: GET /api/stocks/search?q=HSBC returns 200 and a JSON array with one stock.
 *    Test 2: GET /api/stocks/search?q=nomatch returns 200 and an empty array.
 *    Test 3: GET /api/stocks/search with a blank q returns 400.
 *    The Stock record has fields: id (Long), symbol, companyName, sector, exchange (all String).
 *    StockResponse has the same fields plus a fromDomain(Stock) factory method."
 */
@WebMvcTest(StockSearchController.class)
class StockSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;

    private final Stock hsbc = new Stock(1L, "HSBA", "HSBC Holdings PLC", "Banking", "LSE");

    @Test
    void search_matchFound_returns200WithResults() throws Exception {
        // TODO: Complete using AI-generated code (reviewed and corrected).
    }

    @Test
    void search_noMatch_returns200WithEmptyArray() throws Exception {
        // TODO: Complete using AI-generated code (reviewed and corrected).
    }

    @Test
    void search_blankQuery_returns400() throws Exception {
        // TODO: Complete using AI-generated code (reviewed and corrected).
    }
}
