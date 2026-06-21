# Lab 02: Introduction to SpringBoot

## Objective

Convert the Module 01 stock application to SpringBoot. Understand what SpringBoot changes (and what it does not change).

## Duration

35 minutes

## What changes from Module 01

| Module 01 | Module 02 |
|-----------|-----------|
| `spring-context` dependency | `spring-boot-starter-parent` + `spring-boot-starter` |
| `@Configuration` + `@ComponentScan` in AppConfig | `@SpringBootApplication` on the main class |
| `new AnnotationConfigApplicationContext(AppConfig.class)` | `SpringApplication.run(StockApplication.class, args)` |
| Manual demo code in `Main.java` | `CommandLineRunner` @Bean — runs after context is ready |
| No configuration file | `application.properties` — loaded automatically |

**What does NOT change:** `Stock`, `StockRepository`, `InMemoryStockRepository`, `StockService`, `StockServiceImpl` are identical to Module 01.

## Steps

### Step 1 — Delete AppConfig (if carried forward from Module 01)

`@SpringBootApplication` includes `@ComponentScan` on the `com.stocks` package. The separate `AppConfig` class is no longer needed.

### Step 2 — Add `@SpringBootApplication` to `StockApplication`

```java
@SpringBootApplication
public class StockApplication { ... }
```

### Step 3 — Implement `main()`

```java
public static void main(String[] args) {
    SpringApplication.run(StockApplication.class, args);
}
```

### Step 4 — Implement `AppProperties`

Add `@Value` injection for `app.service-name`, `app.version`, and `app.max-stocks`. Add getters.

### Step 5 — Add properties to `application.properties`

```properties
app.service-name=Stock Service
app.version=2.0.0
app.max-stocks=500
```

### Step 6 — Complete the `CommandLineRunner` @Bean

Add `@Bean` to the `demo()` method. Inject `StockService` and `AppProperties`. Add four or more stocks, print them, look up one by ID, and demonstrate duplicate rejection.

## Running

```bash
mvn spring-boot:run
```

## Acceptance Criteria

- [ ] Application starts with SpringBoot (look for the "Started StockApplication" log line)
- [ ] Properties from `application.properties` appear in the output
- [ ] Four or more stocks are added and printed
- [ ] Duplicate symbol is rejected
- [ ] No `AppConfig.java` (or at least no `@ComponentScan` in it — redundant with `@SpringBootApplication`)

## Key Questions

1. What does `@SpringBootApplication` do that `@Configuration + @ComponentScan` does not?
2. Can you override `app.service-name` without editing `application.properties`? How?
3. Why is `CommandLineRunner` better than putting demo code in `main()` directly?
