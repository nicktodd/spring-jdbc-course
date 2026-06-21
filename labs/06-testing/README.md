# Module 06 Lab — Testing Spring Boot Applications

## Objective
Write unit tests for `StockServiceImpl` using Mockito and slice tests for `StockController`
using `@WebMvcTest` and `MockMvc`. All application code is provided — this lab is entirely
about writing the tests.

## Prerequisites
- Module 05 solution understood (the stock application is fully working)
- `spring-boot-starter-test` is already on the classpath (included in `pom.xml`)

## Steps

### Step 1 — Complete `StockServiceImplTest`
Open `src/test/java/com/stocks/service/StockServiceImplTest.java`.

The test class is already set up with `@ExtendWith(MockitoExtension.class)`, `@Mock` fields,
and `@InjectMocks`. Complete each test method:

**`setUp()`**
- Initialise the `hsbc` field:
  ```java
  hsbc = new Stock(1L, "HSBA", "HSBC Holdings PLC", "Banking", "LSE");
  ```

**`addStock_uniqueSymbol_savesAndReturns()`**
1. `when(stockRepository.findBySymbol("HSBA")).thenReturn(Optional.empty())`
2. `when(stockRepository.save(any())).thenReturn(hsbc)`
3. Call `service.addStock(new Stock(null, "HSBA", "HSBC Holdings PLC", "Banking", "LSE"))`
4. `assertThat(result.id()).isEqualTo(1L)`
5. `verify(stockRepository).save(any(Stock.class))`

**`addStock_duplicateSymbol_throwsIllegalArgument()`**
1. `when(stockRepository.findBySymbol("HSBA")).thenReturn(Optional.of(hsbc))`
2. `assertThatThrownBy(() -> service.addStock(...)).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("HSBA")`
3. `verify(stockRepository, never()).save(any())`

**`getAllStocks_delegatesToRepository()`**
1. `when(stockRepository.findAll()).thenReturn(List.of(hsbc))`
2. Call `service.getAllStocks()`
3. `assertThat(result).hasSize(1)`
4. `assertThat(result.getFirst().symbol()).isEqualTo("HSBA")`

**`getStockById_notFound_returnsEmpty()`**
1. `when(stockRepository.findById(99L)).thenReturn(Optional.empty())`
2. `assertThat(service.getStockById(99L)).isEmpty()`

### Step 2 — Complete `StockControllerTest`
Open `src/test/java/com/stocks/controller/StockControllerTest.java`.

The class already has `@WebMvcTest(StockController.class)`, an injected `MockMvc`, and a
`@MockBean StockService`. Complete each test method:

**`getAll_returns200()`**
1. `when(stockService.getAllStocks()).thenReturn(List.of(hsbc))`
2. Perform `GET /api/stocks` and expect:
   - `status().isOk()`
   - `jsonPath("$[0].symbol").value("HSBA")`

**`getById_found_returns200()`**
1. `when(stockService.getStockById(1L)).thenReturn(Optional.of(hsbc))`
2. Perform `GET /api/stocks/1` and expect:
   - `status().isOk()`
   - `jsonPath("$.companyName").value("HSBC Holdings PLC")`

**`getById_notFound_returns404()`**
1. `when(stockService.getStockById(99L)).thenReturn(Optional.empty())`
2. Perform `GET /api/stocks/99` and expect `status().isNotFound()`

**`create_valid_returns201()`**
1. `when(stockService.addStock(any())).thenReturn(hsbc)`
2. Build a JSON body string:
   ```json
   { "symbol": "HSBA", "companyName": "HSBC Holdings PLC", "sector": "Banking", "exchange": "LSE" }
   ```
3. Perform `POST /api/stocks` with `contentType(APPLICATION_JSON)` and expect:
   - `status().isCreated()`
   - `header().exists("Location")`

**`create_missingSymbol_returns400()`**
1. Build a JSON body without the `symbol` field
2. Perform `POST /api/stocks` and expect:
   - `status().isBadRequest()`
   - `jsonPath("$.fields.symbol").exists()`

### Step 3 — Run the tests
```bash
mvn test
```

Run a single class:
```bash
mvn test -Dtest=StockServiceImplTest
mvn test -Dtest=StockControllerTest
```

All five service tests and five controller tests should pass with no application context
errors and no database connection required.

## Acceptance Criteria
- All tests in `StockServiceImplTest` pass
- All tests in `StockControllerTest` pass
- `StockServiceImplTest` makes no HTTP calls and starts no Spring context
- `StockControllerTest` makes no database calls (`StockService` is a `@MockBean`)
- A duplicate-symbol POST returns `400` with the error message in the response body

## Key Questions
1. Why does `@WebMvcTest` not require a running database?
2. What is the difference between `@Mock` and `@MockBean`?
3. Why use `verify(stockRepository, never()).save(any())` in the duplicate test?
4. What does `jsonPath("$[0].symbol")` mean — what does `$` refer to?
