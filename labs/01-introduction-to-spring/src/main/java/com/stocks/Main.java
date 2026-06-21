package com.stocks;

import com.stocks.config.AppConfig;
import com.stocks.model.Stock;
import com.stocks.service.StockService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Module 01 Lab entry point.
 *
 * TODO:
 *   Once you have implemented StockRepository, StockService, and AppConfig,
 *   complete this main method to:
 *
 *   1. Create an AnnotationConfigApplicationContext using AppConfig.class.
 *   2. Retrieve a StockService bean from the context.
 *   3. Add at least three stocks using addStock().
 *   4. Print all stocks using getAllStocks().
 *   5. Retrieve one stock by ID and print it.
 *   6. Demonstrate what happens when you try to add a stock with a duplicate symbol.
 *
 * Run with:  mvn compile exec:java -Dexec.mainClass="com.stocks.Main"
 */
public class Main {

    public static void main(String[] args) {

        // TODO: create the Spring application context

        // TODO: get the StockService bean

        // TODO: add sample stocks - use symbols like "BARC.L", "LLOY.L", "HSBA.L"
        //       with sectors like "Financials" and exchange "LSE"

        // TODO: print all stocks

        // TODO: demonstrate duplicate symbol rejection
    }
}
