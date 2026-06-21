package com.stocks.model;

/**
 * Stock is a Java 21 record - an immutable value object.
 * The compiler generates constructor, accessors, equals, hashCode, and toString.
 */
public record Stock(
        Long id,
        String symbol,
        String companyName,
        String sector,
        String exchange
) {}
