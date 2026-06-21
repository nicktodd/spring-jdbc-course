# Module 07 Lab — Using Generative AI to Extend a Spring Boot Application

## Objective
Use an AI tool (GitHub Copilot, Claude, or ChatGPT) to implement a stock search endpoint,
then review and correct the AI-generated output before integrating it. This lab is as much
about the review process as it is about the code itself.

## Prerequisites
- Module 06 solution working (all existing code is provided and unchanged)
- MySQL running with `stocksdb` database
- Access to an AI tool: GitHub Copilot in your IDE, Claude (claude.ai), or ChatGPT

## Overview

You will add one new feature: **search stocks by company name**.

```
GET /api/stocks/search?q=HSBC
→ [ { "id": 1, "symbol": "HSBA", "companyName": "HSBC Holdings PLC", ... } ]
```

The interface declarations are already in place — you need to implement the three
bodies marked `TODO` and complete the test class.

---

## Steps

### Step 1 — Implement the repository query

Open `repository/JdbcStockRepository.java`. Find `findByCompanyNameContaining()`.

**Try it yourself first**, then use AI to verify or help:

```
I have a Spring JdbcTemplate repository. Add a method that searches stocks
by company name using a case-insensitive LIKE query.
Table: stock. Columns: id, symbol, company_name, sector, exchange.
Use a parameterised query (no string concatenation). Return List<Stock> via a RowMapper.
```

> **Review checklist for the SQL:**
> - [ ] Is the query parameterised with `?` (not string concatenation)?
> - [ ] Does it handle the `%` wildcard correctly?
> - [ ] Does it reuse the existing `stockRowMapper`?

### Step 2 — Implement the service method

Open `service/StockServiceImpl.java`. Find `searchByName()`.

Use this prompt:
```
Add a searchByName(String query) method to a Spring @Service class.
It should delegate to stockRepository.findByCompanyNameContaining(query)
and log at DEBUG level using SLF4J: "Searching stocks by name: query={}", query.
```

> **Review checklist:**
> - [ ] Does it delegate to the repository (no SQL in the service)?
> - [ ] Is logging at the right level (DEBUG, not INFO — this is a read query)?
> - [ ] Does it handle null/blank query, or does the controller handle that?

### Step 3 — Implement the controller

Open `controller/StockSearchController.java`. The class and constructor are provided.

Use the prompt already written in the class Javadoc, or adapt it. Then review the output:

> **Review checklist for the controller:**
> - [ ] Is the endpoint `GET /api/stocks/search` (not a new base path)?
> - [ ] Does it use `@RequestParam String q` (not `@PathVariable`)?
> - [ ] Does it return `List<StockResponse>` (mapped via `StockResponse::fromDomain`)?
> - [ ] Does it validate that `q` is not blank and return `400` if so?
> - [ ] Does it add a `try/catch` that duplicates `GlobalExceptionHandler`? (Remove it if so.)
> - [ ] Does it have a `Logger` field using `LoggerFactory.getLogger(...)`?

### Step 4 — Complete the tests

Open `src/test/java/com/stocks/controller/StockSearchControllerTest.java`.

Use the prompt in the class Javadoc to generate the three test bodies, then review:

> **Review checklist for the tests:**
> - [ ] Does the test use `@WebMvcTest(StockSearchController.class)` (already set)?
> - [ ] Does the happy-path test mock `stockService.searchByName("HSBC")`?
> - [ ] Does the empty-result test assert `$` is an empty array (not 404)?
> - [ ] Does the blank-query test send `q=   ` (spaces, not an empty string)?
> - [ ] Are there any unnecessary `@SpringBootTest` or database connections added?

### Step 5 — Run the tests and the application

```bash
# Tests only (no MySQL required)
mvn test -Dtest=StockSearchControllerTest

# Full application
mvn spring-boot:run
```

Test the endpoint:
```bash
# Search for stocks containing "HSBC"
curl "http://localhost:8080/api/stocks/search?q=HSBC"

# Case-insensitive: "holdings" should also match HSBC Holdings PLC
curl "http://localhost:8080/api/stocks/search?q=holdings"

# No match — should return empty array, not 404
curl "http://localhost:8080/api/stocks/search?q=zzz"

# Blank query — should return 400
curl "http://localhost:8080/api/stocks/search?q="
```

---

## Acceptance Criteria
- `GET /api/stocks/search?q=hsbc` returns matching stocks (case-insensitive)
- `GET /api/stocks/search?q=zzz` returns `[]` with status `200`
- `GET /api/stocks/search?q=` returns `400 Bad Request`
- SQL query uses `?` parameters — no string concatenation
- All three tests in `StockSearchControllerTest` pass
- No `try/catch` blocks in `StockSearchController` that duplicate `GlobalExceptionHandler`

---

## Reflection Questions
1. Did the AI use string concatenation to build the SQL, or parameterised queries? Why does it matter?
2. Did the AI add error handling that already existed in `GlobalExceptionHandler`?
3. Did the AI use the correct HTTP method and response status for a search endpoint?
4. What was the most useful part of the prompt — and what would you change next time?
