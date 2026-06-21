package com.stocks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Value("${app.service-name:Stock Service}")
    private String serviceName;

    @Value("${app.version:1.0.0}")
    private String version;

    @Value("${app.max-stocks:500}")
    private int maxStocks;

    public String getServiceName() { return serviceName; }
    public String getVersion()     { return version; }
    public int    getMaxStocks()   { return maxStocks; }
}
