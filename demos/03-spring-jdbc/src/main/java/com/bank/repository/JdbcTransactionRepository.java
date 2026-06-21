package com.bank.repository;

import com.bank.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

/**
 * JdbcTransactionRepository uses JdbcTemplate to persist transactions in MySQL.
 *
 * Notice the pattern is identical to JdbcAccountRepository:
 *   - Constructor-inject JdbcTemplate
 *   - Define a RowMapper lambda to convert ResultSet rows to records
 *   - Use ? placeholders in all SQL - never string concatenation
 *   - Use KeyHolder to capture the auto-generated id on INSERT
 */
@Repository
public class JdbcTransactionRepository implements TransactionRepository {

    private final JdbcTemplate jdbc;

    public JdbcTransactionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Transaction> transactionRowMapper = (rs, rowNum) -> new Transaction(
            rs.getLong("id"),
            rs.getLong("account_id"),
            rs.getBigDecimal("amount"),
            rs.getString("transaction_type"),
            rs.getString("description"),
            rs.getTimestamp("transaction_date").toLocalDateTime()
    );

    @Override
    public List<Transaction> findByAccountId(Long accountId) {
        return jdbc.query(
                "SELECT * FROM transaction WHERE account_id = ? ORDER BY transaction_date DESC",
                transactionRowMapper,
                accountId
        );
    }

    @Override
    public Transaction save(Transaction transaction) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO transaction (account_id, amount, transaction_type, description, transaction_date) " +
                    "VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            ps.setLong(1, transaction.accountId());
            ps.setBigDecimal(2, transaction.amount());
            ps.setString(3, transaction.transactionType());
            ps.setString(4, transaction.description());
            ps.setTimestamp(5, Timestamp.valueOf(transaction.transactionDate()));
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return new Transaction(generatedId, transaction.accountId(), transaction.amount(),
                transaction.transactionType(), transaction.description(), transaction.transactionDate());
    }
}
