# Module 04 Demo — Account REST API

## Running

```bash
# Ensure MySQL is running with bankdb, appuser/apppass credentials
mvn spring-boot:run
```

The embedded Tomcat server starts on **port 8080**.

---

## API Endpoints

### Accounts

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/accounts` | List all accounts |
| GET | `/api/accounts/{id}` | Get account by id |
| POST | `/api/accounts` | Create a new account |

### Transactions

| Method | URL | Description |
|--------|-----|-------------|
| GET | `/api/accounts/{id}/transactions` | Get transaction history |
| POST | `/api/accounts/{id}/deposits` | Deposit money |
| POST | `/api/accounts/{id}/withdrawals` | Withdraw money |

---

## Example curl Commands

```bash
# List all accounts
curl http://localhost:8080/api/accounts

# Get account by id
curl http://localhost:8080/api/accounts/1

# Create a new account
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{
    "accountNumber": "ACC-010",
    "holderName": "Carol White",
    "accountType": "SAVINGS",
    "balance": 0,
    "openedDate": "2024-06-01"
  }'

# Deposit money
curl -X POST http://localhost:8080/api/accounts/1/deposits \
  -H "Content-Type: application/json" \
  -d '{"amount": 500.00, "description": "Bonus payment"}'

# Withdraw money
curl -X POST http://localhost:8080/api/accounts/1/withdrawals \
  -H "Content-Type: application/json" \
  -d '{"amount": 50.00, "description": "Coffee"}'

# Get transaction history
curl http://localhost:8080/api/accounts/1/transactions

# Duplicate account (returns 400 Bad Request)
curl -X POST http://localhost:8080/api/accounts \
  -H "Content-Type: application/json" \
  -d '{"accountNumber": "ACC-001", "holderName": "Duplicate", "accountType": "CURRENT", "balance": 0, "openedDate": "2024-01-01"}'

# Account not found (returns 404)
curl http://localhost:8080/api/accounts/999
```
