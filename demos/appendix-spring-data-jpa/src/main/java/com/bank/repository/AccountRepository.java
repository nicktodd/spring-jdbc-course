package com.bank.repository;

import com.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for Account.
 *
 * JpaRepository<Account, Long> provides all standard CRUD operations out of the box:
 *   save(account)       — INSERT or UPDATE
 *   findById(id)        — SELECT WHERE id = ?
 *   findAll()           — SELECT *
 *   deleteById(id)      — DELETE WHERE id = ?
 *   count()             — SELECT COUNT(*)
 *
 * Spring generates a complete implementation at startup — no SQL, no RowMapper,
 * no @Repository annotation needed.
 *
 * Derived query methods: Spring reads the method name and generates the SQL.
 * findByAccountNumber("ACC-001") → SELECT * FROM account WHERE account_number = ?
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);
}
