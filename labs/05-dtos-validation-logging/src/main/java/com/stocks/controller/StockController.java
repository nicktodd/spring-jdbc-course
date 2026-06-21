package com.stocks.controller;

import com.stocks.dto.CreateStockRequest;
import com.stocks.dto.StockResponse;
import com.stocks.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * TODO 1: Add @RestController and @RequestMapping("/api/stocks").
 *
 * TODO 2: Add StockService field and constructor.
 *
 * TODO 3: Implement getAllStocks():
 *   - @GetMapping
 *   - Return List<StockResponse> by mapping service.getAllStocks() via StockResponse::fromDomain
 *   - Hint: use .stream().map(StockResponse::fromDomain).toList()
 *
 * TODO 4: Implement getStockById(@PathVariable Long id):
 *   - @GetMapping("/{id}")
 *   - Return ResponseEntity<StockResponse> — map to StockResponse, return 404 if absent
 *
 * TODO 5: Implement createStock(@Valid @RequestBody CreateStockRequest request):
 *   - @PostMapping
 *   - Map request → new Stock(null, request.symbol(), ...)
 *   - Call service.addStock(stock)
 *   - Return 201 Created + Location header + StockResponse body
 *
 * TODO 6: Add a SLF4J Logger field:
 *   private static final Logger log = LoggerFactory.getLogger(StockController.class);
 *   Log at INFO when createStock() is called (log the symbol).
 */
public class StockController {
    // TODO 2: field and constructor
    // TODO 6: Logger
    // TODO 3: getAllStocks
    // TODO 4: getStockById
    // TODO 5: createStock
}
