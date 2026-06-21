package com.stocks.repository;

import com.stocks.model.HistoricalPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricalPriceRepository extends JpaRepository<HistoricalPrice, Long> {

    List<HistoricalPrice> findByStockIdOrderByPriceDateDesc(Long stockId);
}
