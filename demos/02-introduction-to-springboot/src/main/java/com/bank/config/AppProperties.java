package com.bank.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * AppProperties demonstrates externalised configuration using @Value.
 *
 * @Value("${property.key}") reads a value from application.properties.
 * The expression ${app.service-name:Default} reads app.service-name, falling
 * back to "Default" if the property is not set.
 *
 * In Module 06 we will see @ConfigurationProperties, which is preferred for
 * grouping multiple related properties into a single typed class.
 *
 * In production, environment-specific values (database URLs, credentials)
 * are supplied via environment variables. Spring Boot maps
 *   APP_SERVICE_NAME  ->  app.service-name
 * automatically (relaxed binding).
 */
@Component
public class AppProperties {

    @Value("${app.service-name:Account Service}")
    private String serviceName;

    @Value("${app.version:1.0.0}")
    private String version;

    @Value("${app.max-accounts:500}")
    private int maxAccounts;

    public String getServiceName() { return serviceName; }
    public String getVersion()     { return version; }
    public int    getMaxAccounts() { return maxAccounts; }
}
