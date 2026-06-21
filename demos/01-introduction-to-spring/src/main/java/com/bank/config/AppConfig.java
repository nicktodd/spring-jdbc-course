package com.bank.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig is the Spring configuration class for this application.
 *
 * @Configuration tells Spring that this class defines the application's configuration.
 * It is the Java-based alternative to the old XML application context files.
 *
 * @ComponentScan tells Spring to scan the "com.bank" package and all sub-packages
 * for classes annotated with @Component, @Service, @Repository, etc. Spring will
 * instantiate those classes and register them as beans in the application context.
 *
 * Without @ComponentScan, Spring would not find AccountServiceImpl or
 * InMemoryAccountRepository even though they have @Service and @Repository.
 */
@Configuration
@ComponentScan("com.bank")
public class AppConfig {
    // No bean method definitions are needed here because all beans are discovered
    // automatically via @ComponentScan and their @Service / @Repository annotations.
    //
    // In Module 03, explicit @Bean methods are useful when configuring infrastructure
    // beans (like DataSource) that Spring Boot otherwise configures automatically.
}
