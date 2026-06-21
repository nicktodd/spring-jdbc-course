package com.stocks.controller;

import com.stocks.dto.AddPriceRequest;
import com.stocks.model.HistoricalPrice;
import com.stocks.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks/{stockId}/prices")
public class PriceController {

    private final StockService stockService;

    public PriceController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<HistoricalPrice> getPrices(@PathVariable Long stockId) {
        return stockService.getPrices(stockId);
    }

    @PostMapping
    public ResponseEntity<HistoricalPrice> addPrice(
            @PathVariable Long stockId,
            @Valid @RequestBody AddPriceRequest request) {
        HistoricalPrice price = new HistoricalPrice(
                stockId, request.priceDate(),
                request.openPrice(), request.closePrice(),
                request.highPrice(), request.lowPrice(),
                request.volume());
        return ResponseEntity.ok(stockService.addPrice(price));
    }
}
