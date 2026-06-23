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

    @Value("${app.service-name:Stock Service}")
    private String serviceName;

    @Value("${app.version:1.0.0}")
    private String version;

    @Value("${app.max-stocks:500}")
    private int maxStocks;

    public String getServiceName() { return serviceName; }
    public String getVersion() { return version; }
    public int getMaxStocks() { return maxStocks; }
}
