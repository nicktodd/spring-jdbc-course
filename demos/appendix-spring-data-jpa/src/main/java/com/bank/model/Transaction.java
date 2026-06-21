package com.bank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transaction JPA entity.
 *
 * accountId stores the foreign key directly as a Long.
 * GenAI sometimes generates a @ManyToOne Account account field instead —
 * that would fetch the entire Account row whenever a Transaction is loaded.
 * Storing only the foreign key (account_id) keeps queries lean.
 */
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Stores the foreign key column directly rather than a full @ManyToOne relationship.
    // This avoids eager loading of Account data on every Transaction query.
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_type", nullable = false, length = 10)
    private String transactionType;

    @Column(length = 200)
    private String description;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    protected Transaction() {}

    public Transaction(Long accountId, BigDecimal amount, String transactionType,
                       String description, LocalDateTime transactionDate) {
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public Long getId()                     { return id; }
    public Long getAccountId()              { return accountId; }
    public BigDecimal getAmount()           { return amount; }
    public String getTransactionType()      { return transactionType; }
    public String getDescription()          { return description; }
    public LocalDateTime getTransactionDate(){ return transactionDate; }
}
