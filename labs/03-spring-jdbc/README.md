# Module 03 Lab — Spring JDBC

## Objective
Replace the in-memory `InMemoryStockRepository` from Module 02 with a real MySQL-backed
`JdbcStockRepository`, and add `HistoricalPrice` persistence using `JdbcHistoricalPriceRepository`.

## Prerequisites
- Module 02 solution understood (or copied as the base)
- MySQL running locally with `stocksdb` database:
  ```sql
  CREATE DATABASE IF NOT EXISTS stocksdb;
  CREATE USER IF NOT EXISTS 'appuser'@'localhost' IDENTIFIED BY 'apppass';
  GRANT ALL PRIVILEGES ON stocksdb.* TO 'appuser'@'localhost';
  ```

## Steps

### Step 1 — Configure the DataSource
Open `application.properties`. The datasource URL, username, and password are already set.
Verify `spring.sql.init.mode=always` is present so `schema.sql` runs on startup.

### Step 2 — Inspect `schema.sql`
Open `src/main/resources/schema.sql`. This creates the `stock` and `historical_price` tables.
`CREATE TABLE IF NOT EXISTS` makes it safe to run on every startup.

### Step 3 — Implement `JdbcStockRepository`
Follow the TODOs in `JdbcStockRepository.java`:
1. Add `@Repository`
2. Add `JdbcTemplate` field + constructor
3. Define `RowMapper<Stock>` lambda (map `id`, `symbol`, `company_name`, `sector`, `exchange`)
4. Implement `findAll()`
5. Implement `findById()` — return `Optional.empty()` if list is empty
6. Implement `findBySymbol()`
7. Implement `save()` — use `KeyHolder` to capture the generated id

### Step 4 — Implement `JdbcHistoricalPriceRepository`
Follow the TODOs in `JdbcHistoricalPriceRepository.java`:
1. Add `@Repository`
2. Add `JdbcTemplate` field + constructor
3. Define `RowMapper<HistoricalPrice>` lambda
4. Implement `findByStockId()`
5. Implement `save()` — use `Date.valueOf()` to convert `LocalDate`

### Step 5 — Implement `StockServiceImpl`
Follow the TODOs:
1. Add `@Service`
2. Add repository fields + constructor
3-7. Implement all service methods (duplicate symbol check in `addStock()`)

### Step 6 — Complete `StockApplication`
Add `@SpringBootApplication`, `main()`, and a `CommandLineRunner` @Bean that:
- Adds 2+ stocks
- Adds 3+ days of historical prices per stock
- Prints all stocks and their price history
- Demonstrates duplicate rejection

### Step 7 — Run and verify
```bash
mvn spring-boot:run
```
Then check MySQL:
```sql
SELECT * FROM stock;
SELECT * FROM historical_price ORDER BY stock_id, price_date;
```

## Acceptance Criteria
- Application starts without errors
- Stock rows appear in `stock` table after each run
- Historical price rows appear in `historical_price` table
- Duplicate symbol is rejected with a meaningful error message
- `StockServiceImpl` does not reference `JdbcTemplate` directly — it only uses the repository interfaces

## Key Questions
1. Why does `StockServiceImpl` not need to change when we swap from in-memory to JDBC?
2. What does `spring.sql.init.mode=always` do? Why is `IF NOT EXISTS` important here?
3. Why do we use `?` in SQL strings instead of building the query with string concatenation?
