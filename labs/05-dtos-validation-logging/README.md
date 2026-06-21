# Module 05 Lab — DTOs, Validation and Logging

## Objective
Improve the Module 04 REST API by introducing DTOs to decouple the HTTP layer from the
domain model, adding Jakarta Bean Validation to reject bad input before it reaches the
service, and adding SLF4J logging to the controller.

## Prerequisites
- Module 04 solution working (all repository/service/controller code is provided)
- MySQL running with `stocksdb` database

## Steps

### Step 1 — Create `CreateStockRequest` DTO
Open `dto/CreateStockRequest.java` and follow the TODOs:
1. Convert the class to a Java **record** with fields: `symbol`, `companyName`, `sector`, `exchange`
2. Add validation annotations:
   - `symbol` — `@NotBlank` + `@Pattern(regexp = "[A-Z]{2,5}", message = "...")`
   - `companyName` — `@NotBlank`
   - `sector` — `@NotBlank`
   - `exchange` — `@NotBlank`

### Step 2 — Create `AddPriceRequest` DTO
Open `dto/AddPriceRequest.java` and follow the TODOs:
1. Convert the class to a Java record with fields:
   `priceDate`, `openPrice`, `closePrice`, `highPrice`, `lowPrice`, `volume`
2. Add validation annotations:
   - `priceDate` — `@NotNull`
   - Price fields — `@NotNull` + `@DecimalMin("0.0001")`
   - `volume` — `@NotNull` + `@Min(0)`

### Step 3 — Create `StockResponse` DTO
Open `dto/StockResponse.java` and follow the TODO:
- Convert the class to a record with fields: `id`, `symbol`, `companyName`, `sector`, `exchange`
- Add a `static StockResponse fromDomain(Stock s)` factory method

### Step 4 — Implement `StockController`
Open `controller/StockController.java` and follow the TODOs:
1. Add `@RestController` and `@RequestMapping("/api/stocks")`
2. Add a `StockService` field and constructor
3. `getAllStocks()` — `@GetMapping`, map results through `StockResponse::fromDomain`
4. `getStockById()` — `@GetMapping("/{id}")`, return `ResponseEntity<StockResponse>`
5. `createStock()` — `@PostMapping`, accept `@Valid @RequestBody CreateStockRequest`,
   map to domain, call service, return `201 Created` with `Location` header
6. Add a SLF4J `Logger` field:
   ```java
   private static final Logger log = LoggerFactory.getLogger(StockController.class);
   ```
   Log at `INFO` when a stock is created, at `DEBUG` when listing all stocks.

### Step 5 — Implement `PriceController`
Open `controller/PriceController.java` and follow the TODOs:
1. Add `@RestController` and `@RequestMapping("/api/stocks/{stockId}")`
2. Add a `StockService` field and constructor
3. `getPrices()` — `@GetMapping("/prices")`, return `List<HistoricalPrice>`
4. `addPrice()` — `@PostMapping("/prices")`, accept `@Valid @RequestBody AddPriceRequest`,
   build a `HistoricalPrice` from `stockId` + request fields, return `200 OK`

### Step 6 — Complete `GlobalExceptionHandler`
Open `controller/GlobalExceptionHandler.java` and follow the TODOs:
1. Add `@RestControllerAdvice`
2. Add a `Logger` field
3. Handle `IllegalArgumentException` → `400 Bad Request` with JSON body:
   `{ "error": "Bad Request", "message": "...", "timestamp": "..." }`
4. Handle `MethodArgumentNotValidException` → `400 Bad Request` with structured field errors:
   ```json
   { "error": "Validation Failed", "fields": { "symbol": "must not be blank" }, "timestamp": "..." }
   ```
   Use `ex.getBindingResult().getFieldErrors()` to collect the field-level messages.

### Step 7 — Complete `StockApplication`
Follow the TODOs:
1. Add `@SpringBootApplication`
2. Add `main()` calling `SpringApplication.run()`
3. Add an `ApplicationRunner` `@Bean` that seeds stocks and prices if the database is empty

### Step 8 — Run and test
```bash
mvn spring-boot:run
```

Test the happy path:
```bash
# List stocks
curl http://localhost:8080/api/stocks

# Create a valid stock
curl -X POST http://localhost:8080/api/stocks \
  -H "Content-Type: application/json" \
  -d '{"symbol":"SHEL","companyName":"Shell PLC","sector":"Energy","exchange":"LSE"}'
```

Test validation:
```bash
# Missing symbol — should return 400 with field errors
curl -X POST http://localhost:8080/api/stocks \
  -H "Content-Type: application/json" \
  -d '{"companyName":"Shell PLC","sector":"Energy","exchange":"LSE"}'

# Invalid symbol format — should return 400
curl -X POST http://localhost:8080/api/stocks \
  -H "Content-Type: application/json" \
  -d '{"symbol":"shell123","companyName":"Shell PLC","sector":"Energy","exchange":"LSE"}'

# Duplicate symbol — should return 400
curl -X POST http://localhost:8080/api/stocks \
  -H "Content-Type: application/json" \
  -d '{"symbol":"HSBA","companyName":"Duplicate","sector":"Banking","exchange":"LSE"}'
```

## Acceptance Criteria
- `POST /api/stocks` with a missing or invalid field returns `400` with a `fields` map in the response body
- `POST /api/stocks` with a duplicate symbol returns `400` with a `message` in the response body
- Successful `POST` returns `201 Created` with a `Location` header and an `AccountResponse` body (no domain fields exposed that should be hidden)
- INFO log line appears in the console when a stock is created
- `StockController` accepts `CreateStockRequest`, not `Stock` directly

## Key Questions
1. Why return `StockResponse` instead of the `Stock` domain record directly?
2. What triggers validation — what annotation do you add to the `@RequestBody` parameter?
3. Which exception does Spring throw when `@Valid` fails, and how does `GlobalExceptionHandler` catch it?
4. What is the difference between `log.info("created {}", symbol)` and `log.info("created " + symbol)`?
