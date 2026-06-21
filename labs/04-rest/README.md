# Module 04 Lab — Stock REST API

## Objective
Add a REST layer to the Module 03 stock application using `@RestController`.

## Prerequisites
- Module 03 solution understood (all repository/service code is provided)
- MySQL running with `stocksdb` database

## Steps

### Step 1 — Add the web dependency
Verify `spring-boot-starter-web` is in `pom.xml`.
This adds Tomcat, Spring MVC, and Jackson JSON in one dependency.

### Step 2 — Implement `StockController`
Follow the TODOs in `StockController.java`:
1. Add `@RestController` and `@RequestMapping("/api/stocks")`
2. Add `StockService` field + constructor
3. `getAllStocks()` — `@GetMapping`, return `List<Stock>`
4. `getStockById()` — `@GetMapping("/{id}")`, return `ResponseEntity<Stock>` (404 if missing)
5. `createStock()` — `@PostMapping`, return `201 Created` with `Location` header

### Step 3 — Implement `PriceController`
Follow the TODOs in `PriceController.java`:
1. `@RestController` + `@RequestMapping("/api/stocks/{stockId}")`
2. `getPrices()` — `@GetMapping("/prices")`
3. `addPrice()` — `@PostMapping("/prices")` — override `stockId` from the path variable

### Step 4 — Add `GlobalExceptionHandler`
Create a new class `GlobalExceptionHandler` in the `controller` package:
- Annotate with `@RestControllerAdvice`
- Handle `IllegalArgumentException` → return `400 Bad Request` with JSON body

### Step 5 — Complete `StockApplication`
Add `@SpringBootApplication`, `main()`, and an `ApplicationRunner` @Bean that seeds the DB.

### Step 6 — Run and test
```bash
mvn spring-boot:run
```

Test with curl:
```bash
curl http://localhost:8080/api/stocks
curl http://localhost:8080/api/stocks/1
curl http://localhost:8080/api/stocks/1/prices

curl -X POST http://localhost:8080/api/stocks \
  -H "Content-Type: application/json" \
  -d '{"symbol":"SHEL","companyName":"Shell PLC","sector":"Energy","exchange":"LSE"}'

curl -X POST http://localhost:8080/api/stocks/1/prices \
  -H "Content-Type: application/json" \
  -d '{"priceDate":"2024-06-10","openPrice":6.25,"closePrice":6.30,"highPrice":6.35,"lowPrice":6.20,"volume":30000000}'

# Duplicate symbol — should return 400
curl -X POST http://localhost:8080/api/stocks \
  -H "Content-Type: application/json" \
  -d '{"symbol":"HSBA","companyName":"Duplicate","sector":"Banking","exchange":"LSE"}'
```

## Acceptance Criteria
- `GET /api/stocks` returns JSON array
- `GET /api/stocks/{id}` returns 200 or 404
- `POST /api/stocks` returns 201 with `Location` header
- Duplicate symbol returns 400 with a JSON error body
- Price history accessible via `GET /api/stocks/{id}/prices`

## Key Questions
1. What does `@RestController` add beyond `@Controller`?
2. What is the difference between `@PathVariable` and `@RequestBody`?
3. Why return `ResponseEntity` instead of the object directly?
4. What converts the Java record to JSON automatically?
