package com.stocks.controller;

import com.stocks.model.HistoricalPrice;
import com.stocks.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO 1: Add @RestController annotation.
 *
 * TODO 2: Add @RequestMapping("/api/stocks/{stockId}") to nest prices under stocks.
 *
 * TODO 3: Declare a final StockService field and constructor.
 *
 * TODO 4: Implement getPrices(@PathVariable Long stockId):
 *   - Annotate with @GetMapping("/prices")
 *   - Return List<HistoricalPrice> from service.getPriceHistory(stockId)
 *
 * TODO 5: Implement addPrice(@PathVariable Long stockId, @RequestBody HistoricalPrice price):
 *   - Annotate with @PostMapping("/prices")
 *   - Build a new HistoricalPrice with stockId from the path (price.stockId() might be null in the body)
 *   - Call service.addPrice(priceWithStockId) and return ResponseEntity.ok(result)
 *
 * TODO 6: Add a GlobalExceptionHandler class (same package) with @RestControllerAdvice
 *   that handles IllegalArgumentException and returns ResponseEntity.badRequest()
 *   with a JSON body containing "error" and "message" fields.
 *   Hint: use Map.of("error", "Bad Request", "message", ex.getMessage())
 */
@RestController
@RequestMapping("/api/stocks/{stockId}")
public class PriceController {

    private final StockService service;

    public PriceController(StockService service) {
        this.service = service;
    }

    @GetMapping("/prices")
    public List<HistoricalPrice> getPrices(@PathVariable Long stockId) {
        return service.getPriceHistory(stockId);
    }

    @PostMapping("/prices")
    public ResponseEntity<HistoricalPrice> addPrice(@PathVariable Long stockId, @RequestBody HistoricalPrice price) {
        HistoricalPrice priceWithStockId = new HistoricalPrice(null, stockId, price.priceDate(),
                price.openPrice(), price.closePrice(), price.highPrice(), price.lowPrice(), price.volume());
        return ResponseEntity.ok(service.addPrice(priceWithStockId));
    }
}
