package com.bank.repository;

import com.bank.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for Transaction.
 *
 * findByAccountIdOrderByTransactionDateDesc(accountId):
 *   Spring parses the method name and generates:
 *   SELECT * FROM transaction WHERE account_id = ? ORDER BY transaction_date DESC
 *
 * No implementation class is needed — Spring does it all.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountIdOrderByTransactionDateDesc(Long accountId);
}
