package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AccountServiceImpl.
 *
 * Key concepts demonstrated:
 *
 * @ExtendWith(MockitoExtension.class)
 *   Activates Mockito annotations. No Spring context is loaded — this test
 *   starts and runs in milliseconds.
 *
 * @Mock
 *   Creates a Mockito mock: a fake object whose methods return default values
 *   (null, 0, empty collections) unless configured with when().thenReturn().
 *
 * @InjectMocks
 *   Creates the real AccountServiceImpl and injects the @Mock fields into it.
 *   We test the real service code, but with fake repositories.
 *
 * when(mock.method(args)).thenReturn(value)
 *   Configures what the mock returns for a given call.
 *
 * verify(mock).method(args)
 *   Asserts the mock was called with those arguments.
 *
 * ArgumentCaptor
 *   Captures the argument passed to a mock so we can assert on its contents.
 *
 * AssertJ: assertThat(actual).isEqualTo(expected)
 *   Fluent assertion library included with spring-boot-starter-test.
 */
@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    // @Mock creates a fake AccountRepository — no database, no SQL.
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    // @InjectMocks creates the real AccountServiceImpl using the mocks above.
    @InjectMocks
    private AccountServiceImpl service;

    // Test data — reused across multiple tests.
    private Account aliceAccount;

    @BeforeEach
    void setUp() {
        aliceAccount = new Account(1L, "ACC-001", "Alice Johnson",
                "CURRENT", new BigDecimal("1000.00"), LocalDate.of(2024, 1, 15));
    }

    // ── openAccount ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("openAccount saves and returns the account when number is unique")
    void openAccount_uniqueNumber_savesAndReturns() {
        // Given: no existing account with this number
        when(accountRepository.findByAccountNumber("ACC-001")).thenReturn(Optional.empty());
        // And: the repository returns the saved account with an id
        when(accountRepository.save(any(Account.class))).thenReturn(aliceAccount);

        // When
        Account result = service.openAccount(
                new Account(null, "ACC-001", "Alice Johnson", "CURRENT",
                        new BigDecimal("1000.00"), LocalDate.of(2024, 1, 15)));

        // Then
        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.accountNumber()).isEqualTo("ACC-001");
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    @DisplayName("openAccount throws when account number already exists")
    void openAccount_duplicateNumber_throwsIllegalArgument() {
        // Given: an account already exists with this number
        when(accountRepository.findByAccountNumber("ACC-001"))
                .thenReturn(Optional.of(aliceAccount));

        // When / Then
        assertThatThrownBy(() -> service.openAccount(
                new Account(null, "ACC-001", "Duplicate", "CURRENT",
                        BigDecimal.ZERO, LocalDate.now())))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ACC-001");

        // And: save should never have been called
        verify(accountRepository, never()).save(any());
    }

    // ── deposit ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("deposit updates balance and records a CREDIT transaction")
    void deposit_validAccount_updatesBalanceAndRecordsTransaction() {
        BigDecimal depositAmount = new BigDecimal("500.00");
        when(accountRepository.findById(1L)).thenReturn(Optional.of(aliceAccount));
        when(accountRepository.save(any())).thenReturn(aliceAccount);

        Transaction savedTxn = new Transaction(10L, 1L, depositAmount, "CREDIT", "Salary", null);
        when(transactionRepository.save(any())).thenReturn(savedTxn);

        // When
        Transaction result = service.deposit(1L, depositAmount, "Salary");

        // Then: the saved account should have an increased balance
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountCaptor.capture());
        assertThat(accountCaptor.getValue().balance())
                .isEqualByComparingTo(new BigDecimal("1500.00"));

        // And: a CREDIT transaction was saved
        ArgumentCaptor<Transaction> txnCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(txnCaptor.capture());
        assertThat(txnCaptor.getValue().transactionType()).isEqualTo("CREDIT");
        assertThat(txnCaptor.getValue().amount()).isEqualByComparingTo(depositAmount);
    }

    // ── withdraw ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("withdraw deducts balance and records a DEBIT transaction")
    void withdraw_sufficientFunds_deductsBalance() {
        BigDecimal amount = new BigDecimal("200.00");
        when(accountRepository.findById(1L)).thenReturn(Optional.of(aliceAccount));
        when(accountRepository.save(any())).thenReturn(aliceAccount);
        when(transactionRepository.save(any())).thenAnswer(inv -> {
            Transaction t = inv.getArgument(0);
            return new Transaction(20L, t.accountId(), t.amount(), t.transactionType(),
                    t.description(), t.transactionDate());
        });

        service.withdraw(1L, amount, "Rent");

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(captor.capture());
        assertThat(captor.getValue().balance()).isEqualByComparingTo(new BigDecimal("800.00"));
    }

    @Test
    @DisplayName("withdraw throws when balance is insufficient")
    void withdraw_insufficientFunds_throwsIllegalArgument() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(aliceAccount));

        assertThatThrownBy(() -> service.withdraw(1L, new BigDecimal("9999.00"), "Too much"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Insufficient funds");

        verify(accountRepository, never()).save(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    @DisplayName("deposit throws when account does not exist")
    void deposit_unknownAccount_throwsIllegalArgument() {
        when(accountRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deposit(99L, new BigDecimal("100.00"), "Test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("99");
    }

    // ── getAllAccounts ───────────────────────────────────────────────────────

    @Test
    @DisplayName("getAllAccounts returns whatever the repository returns")
    void getAllAccounts_delegatesToRepository() {
        when(accountRepository.findAll()).thenReturn(List.of(aliceAccount));

        List<Account> result = service.getAllAccounts();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().holderName()).isEqualTo("Alice Johnson");
    }
}
