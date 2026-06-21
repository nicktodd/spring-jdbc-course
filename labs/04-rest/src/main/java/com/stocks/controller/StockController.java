package com.stocks.controller;

import com.stocks.model.Stock;
import com.stocks.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * TODO 1: Add @RestController annotation.
 *
 * TODO 2: Add @RequestMapping("/api/stocks") to map all methods to this base URL.
 *
 * TODO 3: Declare a final StockService field and constructor that accepts it.
 *
 * TODO 4: Implement getAllStocks():
 *   - Annotate with @GetMapping
 *   - Return List<Stock> from service.getAllStocks()
 *   - No ResponseEntity needed — Spring returns 200 OK automatically
 *
 * TODO 5: Implement getStockById(@PathVariable Long id):
 *   - Annotate with @GetMapping("/{id}")
 *   - Return ResponseEntity<Stock>
 *   - Use service.getStockById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build())
 *
 * TODO 6: Implement createStock(@RequestBody Stock stock):
 *   - Annotate with @PostMapping
 *   - Call service.addStock(stock) to get the saved stock
 *   - Build a URI using ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.id()).toUri()
 *   - Return ResponseEntity.created(location).body(saved)
 */
public class StockController {

    // TODO 3: Add field and constructor

    // TODO 4: getAllStocks

    // TODO 5: getStockById

    // TODO 6: createStock
}
