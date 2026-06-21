# Lab 01: Introduction to Spring

## Objective

Wire a small Spring application using Inversion of Control and Dependency Injection, without using Spring Boot.

## Duration

40 minutes

## What you are building

A CLI application that manages a list of stocks using Spring's IoC container. No database, no web server — just Spring managing the wiring of your classes.

## Steps

### Step 1 — Convert `Stock` to a record

Open `Stock.java`. Replace the class with a Java 21 record:

```java
public record Stock(Long id, String symbol, String companyName, String sector, String exchange) {}
```

### Step 2 — Define `StockRepository`

Open `StockRepository.java` and add the four method signatures listed in the TODO comment.

### Step 3 — Implement `InMemoryStockRepository`

Open `InMemoryStockRepository.java`:
1. Add `implements StockRepository` to the class declaration.
2. Implement all four methods using the provided `store` Map and `idSequence`.
3. In `save()`, if `stock.id()` is `null`, create a new Stock with the next ID.

### Step 4 — Define `StockService`

Open `StockService.java` and add the three method signatures listed in the TODO comment.

### Step 5 — Implement `StockServiceImpl`

Open `StockServiceImpl.java`:
1. Add `implements StockService`.
2. Declare `private final StockRepository stockRepository`.
3. Add a constructor that receives `StockRepository` (Spring will inject it).
4. Implement all three methods. In `addStock()`, throw `IllegalArgumentException` if the symbol already exists.

### Step 6 — Configure Spring

Open `AppConfig.java` and add `@Configuration` and `@ComponentScan("com.stocks")`.

### Step 7 — Complete `Main`

Open `Main.java` and complete the TODO items to run the application with Spring.

## Running

```bash
mvn compile exec:java -Dexec.mainClass="com.stocks.Main"
```

## Acceptance Criteria

- [ ] Application starts and the Spring context is created
- [ ] Three or more stocks are added and printed
- [ ] `getStockById()` returns the correct stock
- [ ] Adding a duplicate symbol prints (or throws) an error rather than creating a duplicate
- [ ] No `new InMemoryStockRepository()` appears anywhere in `Main.java` — Spring creates it

## Key Questions

1. Where exactly does Spring create the `InMemoryStockRepository` instance?
2. If you annotated `InMemoryStockRepository` with `@Component` instead of `@Repository`, would the application still work? Why?
3. What would happen if two classes implemented `StockRepository` and neither was annotated `@Primary`?
