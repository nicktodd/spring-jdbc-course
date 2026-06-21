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
public class PriceController {
    // TODO 2: field and constructor
    // TODO 3: getPrices
    // TODO 4: addPrice
}
