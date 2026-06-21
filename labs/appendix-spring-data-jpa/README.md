# Module 07 Lab — Spring Data JPA

## Objective
Convert the Module 06 stock application from `JdbcTemplate` to Spring Data JPA.
You will add JPA annotations to the domain classes, declare repository interfaces,
add `@Transactional` to the service, and write a `@DataJpaTest` for the repository.

## Prerequisites
- Module 06 solution understood (all controller and DTO code is provided and unchanged)
- MySQL running with `stocksdb` database
- `spring-boot-starter-data-jpa` and `h2` (test scope) are already in `pom.xml`

## Steps

### Step 1 — Annotate the `Stock` entity
Open `model/Stock.java` and follow the TODOs:
1. Add `@Entity` to the class
2. Add `@Table(name = "stock")`
3. Annotate `id` with `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)`
4. Annotate `symbol` with `@Column(name = "symbol", nullable = false, unique = true, length = 10)`
5. Annotate `companyName` with `@Column(name = "company_name", nullable = false, length = 200)`
6. Annotate `sector` with `@Column(nullable = false, length = 50)`
7. Annotate `exchange` with `@Column(nullable = false, length = 20)`
8. Add a `protected Stock() {}` no-arg constructor (required by JPA for proxy creation)

### Step 2 — Annotate the `HistoricalPrice` entity
Open `model/HistoricalPrice.java` and follow the TODOs:
1. Add `@Entity` and `@Table(name = "historical_price")`
2. Annotate `id` with `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)`
3. Annotate `stockId` with `@Column(name = "stock_id", nullable = false)`
4. Annotate `priceDate` with `@Column(name = "price_date", nullable = false)`
5. Annotate the four price fields with `@Column(nullable = false, precision = 15, scale = 4)`
6. Annotate `volume` with `@Column(nullable = false)`
7. Add a `protected HistoricalPrice() {}` no-arg constructor

### Step 3 — Add derived query methods to the repositories
Open `repository/StockRepository.java`:
- Add: `Optional<Stock> findBySymbol(String symbol);`
  Spring generates `SELECT * FROM stock WHERE symbol = ?` from the method name.

Open `repository/HistoricalPriceRepository.java`:
- Add: `List<HistoricalPrice> findByStockIdOrderByPriceDateDesc(Long stockId);`
  Spring generates `SELECT * FROM historical_price WHERE stock_id = ? ORDER BY price_date DESC`.

### Step 4 — Add `@Transactional` to `StockServiceImpl`
Open `service/StockServiceImpl.java` and follow the TODOs:
1. Add `@Transactional` to the class — wraps every public method in a database transaction
2. Add `@Transactional(readOnly = true)` to `getAllStocks()`, `getStockById()`, and `getPrices()`
3. In `addStock()`, call `stockRepository.findBySymbol(stock.getSymbol())` and throw
   `IllegalArgumentException` if the symbol is already present
4. In `addPrice()`, call `priceRepository.save(price)` and return the saved entity

### Step 5 — Write `StockRepositoryTest`
Open `src/test/java/com/stocks/repository/StockRepositoryTest.java`.

The class already has `@DataJpaTest` and an `@Autowired StockRepository`. Complete the three tests:

**`save_assignsId()`**
- Save a new `Stock("HSBA", "HSBC Holdings PLC", "Banking", "LSE")`
- Assert that `saved.getId()` is not null and greater than 0

**`findBySymbol_found()`**
- Save a stock with symbol `"BP"`
- Call `stockRepository.findBySymbol("BP")`
- Assert the result is present and `getCompanyName()` equals `"BP PLC"`

**`findBySymbol_notFound()`**
- Call `stockRepository.findBySymbol("UNKNOWN")` without saving anything
- Assert the result is empty

### Step 6 — Run and test
```bash
# Run the repository tests (H2 in-memory — no MySQL required)
mvn test -Dtest=StockRepositoryTest

# Start the full application against MySQL
mvn spring-boot:run
```

Test the endpoints:
```bash
curl http://localhost:8080/api/stocks
curl http://localhost:8080/api/stocks/1
curl http://localhost:8080/api/stocks/1/prices

curl -X POST http://localhost:8080/api/stocks \
  -H "Content-Type: application/json" \
  -d '{"symbol":"SHEL","companyName":"Shell PLC","sector":"Energy","exchange":"LSE"}'

# Duplicate symbol — should return 400
curl -X POST http://localhost:8080/api/stocks \
  -H "Content-Type: application/json" \
  -d '{"symbol":"HSBA","companyName":"Duplicate","sector":"Banking","exchange":"LSE"}'
```

## Acceptance Criteria
- Application starts and serves requests against MySQL
- `StockRepositoryTest` passes using H2 — no MySQL connection needed for tests
- Duplicate symbol is rejected with `400 Bad Request`
- `StockServiceImpl` has `@Transactional` on the class and `readOnly = true` on all read methods
- No `JdbcTemplate`, `RowMapper`, or raw SQL anywhere in the codebase

## Key Questions
1. Why does JPA require a no-arg constructor on `@Entity` classes?
2. What does `@Transactional(readOnly = true)` tell Hibernate?
3. How does Spring know what SQL to run for `findBySymbol(String symbol)`?
4. `@DataJpaTest` uses H2, not MySQL — what creates the schema during these tests?
