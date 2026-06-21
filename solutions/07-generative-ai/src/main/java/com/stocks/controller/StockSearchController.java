package com.stocks.controller;

import com.stocks.dto.StockResponse;
import com.stocks.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockSearchController {

    private static final Logger log = LoggerFactory.getLogger(StockSearchController.class);

    private final StockService stockService;

    public StockSearchController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<StockResponse>> search(@RequestParam String q) {
        if (q == null || q.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        log.info("Stock search: q={}", q);
        List<StockResponse> results = stockService.searchByName(q)
                .stream()
                .map(StockResponse::fromDomain)
                .toList();
        return ResponseEntity.ok(results);
    }
}
