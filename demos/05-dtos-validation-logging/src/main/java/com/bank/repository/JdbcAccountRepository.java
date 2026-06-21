package com.bank.repository;

import com.bank.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * JdbcAccountRepository replaces InMemoryAccountRepository from Modules 01/02.
 *
 * Key points to notice:
 *
 * 1. JdbcTemplate is injected via constructor - SpringBoot auto-configures it
 *    from the datasource properties. We do not create it ourselves.
 *
 * 2. RowMapper<Account> converts each ResultSet row into an Account record.
 *    It is a lambda because RowMapper is a functional interface.
 *    Column names match the bank-schema.sql definition exactly.
 *
 * 3. jdbc.query() returns a List - suitable for findAll().
 *    jdbc.queryForObject() returns exactly one result - suitable for findById().
 *    jdbc.update() runs INSERT/UPDATE/DELETE and returns rows affected.
 *
 * 4. KeyHolder captures the auto-generated primary key from an INSERT.
 *    We need it to return the saved Account with its database-assigned ID.
 *
 * 5. All queries use ? placeholders. NEVER use string concatenation to build SQL -
 *    that opens a SQL injection vulnerability.
 *
 * 6. AccountServiceImpl is completely unchanged from Module 02.
 *    This demonstrates the value of programming to the AccountRepository interface.
 */
@Repository
public class JdbcAccountRepository implements AccountRepository {

    private final JdbcTemplate jdbc;

    // SpringBoot auto-configures JdbcTemplate from application.properties.
    // We simply declare it as a constructor parameter.
    public JdbcAccountRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // RowMapper is defined once as a field and reused across all query methods.
    // The lambda receives a ResultSet (one row) and a row number.
    private final RowMapper<Account> accountRowMapper = (rs, rowNum) -> new Account(
            rs.getLong("id"),
            rs.getString("account_number"),
            rs.getString("holder_name"),
            rs.getString("account_type"),
            rs.getBigDecimal("balance"),
            rs.getDate("opened_date").toLocalDate()
    );

    @Override
    public List<Account> findAll() {
        // jdbc.query() executes the SQL and maps every row to an Account.
        return jdbc.query("SELECT * FROM account ORDER BY id", accountRowMapper);
    }

    @Override
    public Optional<Account> findById(Long id) {
        // queryForObject throws EmptyResultDataAccessException if no row found.
        // We catch it and return Optional.empty() to give callers a clean API.
        var results = jdbc.query(
                "SELECT * FROM account WHERE id = ?",
                accountRowMapper,
                id
        );
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        var results = jdbc.query(
                "SELECT * FROM account WHERE account_number = ?",
                accountRowMapper,
                accountNumber
        );
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    @Override
    public Account save(Account account) {
        if (account.id() == null) {
            // INSERT - KeyHolder captures the auto-generated primary key.
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbc.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO account (account_number, holder_name, account_type, balance, opened_date) " +
                        "VALUES (?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
                ps.setString(1, account.accountNumber());
                ps.setString(2, account.holderName());
                ps.setString(3, account.accountType());
                ps.setBigDecimal(4, account.balance());
                ps.setDate(5, Date.valueOf(account.openedDate()));
                return ps;
            }, keyHolder);
            Long generatedId = keyHolder.getKey().longValue();
            return new Account(generatedId, account.accountNumber(), account.holderName(),
                    account.accountType(), account.balance(), account.openedDate());
        } else {
            // UPDATE - used when balance changes after a deposit or withdrawal.
            jdbc.update(
                    "UPDATE account SET balance = ? WHERE id = ?",
                    account.balance(), account.id()
            );
            return account;
        }
    }
}
