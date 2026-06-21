package com.bank.repository;

import com.bank.model.Transaction;
import java.util.List;

/** Repository for transaction data access. New in Module 03. */
public interface TransactionRepository {
    List<Transaction> findByAccountId(Long accountId);
    Transaction save(Transaction transaction);
}
