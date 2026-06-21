package com.stocks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * AppProperties reads configuration from application.properties.
 *
 * TODO:
 *   1. Add @Value("${app.service-name:Stock Service}") to inject serviceName.
 *   2. Add @Value("${app.version:1.0.0}") to inject version.
 *   3. Add @Value("${app.max-stocks:500}") to inject maxStocks.
 *   4. Add getters for each field.
 *   5. Add the corresponding properties to application.properties.
 */
@Component
public class AppProperties {

    // TODO: inject app.service-name
    private String serviceName;

    // TODO: inject app.version
    private String version;

    // TODO: inject app.max-stocks
    private int maxStocks;

    // TODO: add getters
}
