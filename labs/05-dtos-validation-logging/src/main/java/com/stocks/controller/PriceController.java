package com.stocks.controller;

import com.stocks.dto.AddPriceRequest;
import com.stocks.model.HistoricalPrice;
import com.stocks.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO 1: Add @RestController and @RequestMapping("/api/stocks/{stockId}").
 *
 * TODO 2: Add StockService field and constructor.
 *
 * TODO 3: Implement getPrices(@PathVariable Long stockId):
 *   - @GetMapping("/prices")
 *   - Return List<HistoricalPrice> from service.getPriceHistory(stockId)
 *
 * TODO 4: Implement addPrice(@PathVariable Long stockId, @Valid @RequestBody AddPriceRequest request):
 *   - @PostMapping("/prices")
 *   - Build a HistoricalPrice record using stockId from path and all fields from request
 *   - Call service.addPrice(price)
 *   - Return ResponseEntity.ok(result)
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
    public ResponseEntity<HistoricalPrice> addPrice(@PathVariable Long stockId, @Valid @RequestBody AddPriceRequest request) {
        HistoricalPrice price = new HistoricalPrice(null, stockId, request.priceDate(),
                request.openPrice(), request.closePrice(), request.highPrice(), request.lowPrice(), request.volume());
        return ResponseEntity.ok(service.addPrice(price));
    }
}
