# AI Prompts Used in This Demo

This file documents the prompts used to generate each component of the
`ReportController` feature, the AI output quality, and what was corrected.
It is a practical example of the AI-assisted development workflow.

---

## 1. Generating the DTO records

**Prompt:**
```
I have a Transaction record with fields: id (Long), accountId (Long), amount (BigDecimal),
transactionType (String, values CREDIT or DEBIT), description (String),
transactionDate (LocalDateTime).

Create two Java 21 records for a bank statement:
- StatementEntry: one transaction line showing date, type, description, amount, runningBalance
- AccountStatement: the full statement with accountNumber, holderName, accountType,
  statementDate (LocalDate), openingBalance, closingBalance, and a List<StatementEntry>
```

**Result:** Correct first time. Minor rename: `generatedDate` → `statementDate`.

---

## 2. Generating the service

**Prompt:**
```
Write a Spring @Service called ReportServiceImpl implementing ReportService.
Constructor-inject AccountRepository and TransactionRepository.

generateStatement(Long accountId) should:
1. Load the Account by id, throw IllegalArgumentException("Account not found: " + id) if missing.
2. Load all transactions for the account sorted oldest-first.
3. Compute a running balance, applying CREDIT as +amount and DEBIT as -amount.
4. Derive openingBalance = current balance minus the net of all transactions.
5. Build a List<StatementEntry> with the running balance on each entry.
6. Return AccountStatement with statementDate = LocalDate.now().

The Account record has: id, accountNumber, holderName, accountType, balance, openedDate.
```

**Issues found in review:**
- Used `Collections.sort()` mutating the repository list → replaced with stream `sorted()`
- Got the sign wrong for DEBIT in the net calculation (subtracted instead of negating) → fixed
- Missing `import java.time.LocalDate` → added

---

## 3. Generating the controller

**Prompt:**
```
Write a Spring @RestController called ReportController mapped to /api/reports.
Constructor-inject ReportService.
Add: GET /api/reports/accounts/{id}/statement returning AccountStatement, 404 if not found.
Add a SLF4J logger. Follow the pattern used in AccountController in the same project.
```

**Issues found in review:**
- AI added a try/catch for IllegalArgumentException inside the method returning 404 —
  this duplicated GlobalExceptionHandler. Removed the try/catch.
  A not-found account throws IllegalArgumentException which GlobalExceptionHandler maps to 400;
  this is acceptable for this demo (a production API would distinguish 404 from 400 more carefully).

---

## 4. Writing the test (ask AI to do it too)

**Prompt:**
```
Write a @WebMvcTest for ReportController using MockMvc and @MockBean ReportService.
Test: GET /api/reports/accounts/1/statement returns 200 with a JSON body.
Test: GET /api/reports/accounts/99/statement returns 400 when the service throws
IllegalArgumentException("Account not found: 99").
Use AssertJ. The AccountStatement record has: accountNumber, holderName, accountType,
statementDate, openingBalance, closingBalance, entries (List).
```

**Result:** Correct first time.

---

## Key observations from this workflow

1. **DTOs are the easiest win** — AI generates clean records reliably.
2. **Business logic needs review** — sign errors and mutation bugs are common.
3. **AI duplicates error handling** — always check if a try/catch is already covered elsewhere.
4. **Always give AI the field names** — hallucinated field names are the most common mistake.
5. **Ask AI to follow an existing pattern** — "follow the pattern in AccountController" produces
   more consistent code than asking from scratch.
