package com.stocks.controller;

import com.stocks.dto.StockResponse;
import com.stocks.service.StockService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * StockSearchController — to be built using an AI tool.
 *
 * This controller should expose one endpoint:
 *   GET /api/stocks/search?q={query}
 *   Returns a List<StockResponse> of stocks whose company name contains the query.
 *   Returns an empty list (not 404) when nothing matches.
 *   Returns 400 if q is blank.
 *
 * Use the following prompt with your AI tool to generate this class, then review
 * the output against the checklist in README.md before adding it here:
 *
 *   "Write a Spring @RestController called StockSearchController mapped to /api/stocks.
 *    Constructor-inject StockService.
 *    Add: GET /api/stocks/search with a required @RequestParam String q.
 *    Call stockService.searchByName(q) and return List<StockResponse> mapped via
 *    StockResponse::fromDomain.
 *    Return 400 Bad Request if q is blank.
 *    Add a SLF4J logger. Follow Spring Boot best practices."
 *
 * TODO: Replace this comment with the reviewed, corrected AI-generated implementation.
 */
@RestController
@RequestMapping("/api/stocks")
public class StockSearchController {

    private final StockService stockService;

    public StockSearchController(StockService stockService) {
        this.stockService = stockService;
    }

    // TODO: Add the search endpoint here.
}
