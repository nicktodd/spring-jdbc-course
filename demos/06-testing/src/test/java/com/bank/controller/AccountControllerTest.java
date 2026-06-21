package com.bank.controller;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Slice test for AccountController using @WebMvcTest.
 *
 * Key concepts:
 *
 * @WebMvcTest(AccountController.class)
 *   Loads ONLY the MVC layer: controllers, filters, GlobalExceptionHandler.
 *   Does NOT load repositories, services, or the database.
 *   Much faster than @SpringBootTest which loads the full application context.
 *
 * @MockBean
 *   Creates a Mockito mock AND registers it as a Spring bean.
 *   The controller receives this mock instead of the real AccountServiceImpl.
 *
 * MockMvc
 *   Auto-configured by @WebMvcTest. Lets us send HTTP requests to the controller
 *   without starting a real server, and assert on the HTTP response.
 *
 * perform(get("/api/accounts"))
 *   Builds and executes a GET request.
 *
 * andExpect(status().isOk())
 *   Asserts the HTTP status code is 200.
 *
 * andExpect(jsonPath("$[0].holderName").value("Alice Johnson"))
 *   Asserts on a field in the JSON response using JSONPath expressions.
 *   $       = root of the document
 *   $[0]    = first element of an array
 *   $.field = field on an object
 */
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;  // Jackson — used to serialise request bodies

    // @MockBean registers this mock in the Spring context so AccountController can use it.
    @MockBean
    private AccountService accountService;

    private final Account alice = new Account(1L, "ACC-001", "Alice Johnson",
            "CURRENT", new BigDecimal("1000.00"), LocalDate.of(2024, 1, 15));

    // ── GET /api/accounts ────────────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/accounts returns 200 and JSON array")
    void getAll_returns200WithList() throws Exception {
        when(accountService.getAllAccounts()).thenReturn(List.of(alice));

        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].holderName").value("Alice Johnson"))
                .andExpect(jsonPath("$[0].accountNumber").value("ACC-001"));
    }

    // ── GET /api/accounts/{id} ───────────────────────────────────────────────

    @Test
    @DisplayName("GET /api/accounts/{id} returns 200 when found")
    void getById_found_returns200() throws Exception {
        when(accountService.getAccountById(1L)).thenReturn(Optional.of(alice));

        mockMvc.perform(get("/api/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountType").value("CURRENT"));
    }

    @Test
    @DisplayName("GET /api/accounts/{id} returns 404 when not found")
    void getById_notFound_returns404() throws Exception {
        when(accountService.getAccountById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/accounts/99"))
                .andExpect(status().isNotFound());
    }

    // ── POST /api/accounts ───────────────────────────────────────────────────

    @Test
    @DisplayName("POST /api/accounts returns 201 with Location header")
    void create_validRequest_returns201() throws Exception {
        when(accountService.openAccount(any(Account.class))).thenReturn(alice);

        String body = """
                {
                  "accountNumber": "ACC-001",
                  "holderName": "Alice Johnson",
                  "accountType": "CURRENT",
                  "balance": 0,
                  "openedDate": "2024-01-15"
                }
                """;

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @DisplayName("POST /api/accounts returns 400 when accountNumber is missing")
    void create_missingAccountNumber_returns400() throws Exception {
        String body = """
                {
                  "holderName": "Alice Johnson",
                  "accountType": "CURRENT",
                  "balance": 0,
                  "openedDate": "2024-01-15"
                }
                """;

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.fields.accountNumber").exists());
    }

    @Test
    @DisplayName("POST /api/accounts returns 400 when service rejects duplicate")
    void create_duplicate_returns400() throws Exception {
        when(accountService.openAccount(any()))
                .thenThrow(new IllegalArgumentException("Account number already exists: ACC-001"));

        String body = """
                {
                  "accountNumber": "ACC-001",
                  "holderName": "Alice Johnson",
                  "accountType": "CURRENT",
                  "balance": 0,
                  "openedDate": "2024-01-15"
                }
                """;

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Account number already exists: ACC-001"));
    }
}
