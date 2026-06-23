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
import com.stocks.model.Stock;
import java.net.URI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private static final Logger log = LoggerFactory.getLogger(StockController.class);
    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping
    public List<StockResponse> getAllStocks() {
        log.debug("Listing all stocks");
        return service.getAllStocks().stream().map(StockResponse::fromDomain).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockResponse> getStockById(@PathVariable Long id) {
        return service.getStockById(id).map(StockResponse::fromDomain).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<StockResponse> createStock(@Valid @RequestBody CreateStockRequest request) {
        log.info("Creating stock: {}", request.symbol());
        Stock saved = service.addStock(new Stock(null, request.symbol(), request.companyName(), request.sector(), request.exchange()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.id()).toUri();
        return ResponseEntity.created(location).body(StockResponse.fromDomain(saved));
    }
}
