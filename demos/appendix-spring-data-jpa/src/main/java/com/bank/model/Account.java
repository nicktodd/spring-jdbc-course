package com.bank.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Account is a JPA @Entity — a Java class that maps directly to a database table.
 *
 * Key differences from the records used in earlier modules:
 *
 *   1. @Entity + @Table tell Hibernate which table this class represents.
 *   2. @Id + @GeneratedValue(IDENTITY) map to an AUTO_INCREMENT primary key.
 *   3. @Column maps fields to columns; use name= when the column name differs from the field.
 *   4. A protected no-arg constructor is required by JPA for proxy creation
 *      (Hibernate creates subclass proxies at runtime; they need to call super()).
 *   5. Fields are mutable (setters) so Hibernate can update them within a @Transactional method.
 *
 * When GenAI scaffolds a Spring + database project it almost always produces
 * classes like this. Understanding these annotations lets you review, adapt,
 * and correct AI-generated code confidently.
 */
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Column(name = "holder_name", nullable = false, length = 100)
    private String holderName;

    @Column(name = "account_type", nullable = false, length = 20)
    private String accountType;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(name = "opened_date", nullable = false)
    private LocalDate openedDate;

    // Required by JPA — do not use directly in application code.
    protected Account() {}

    // Use this constructor when creating a new account (id is null until saved).
    public Account(String accountNumber, String holderName, String accountType,
                   BigDecimal balance, LocalDate openedDate) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.accountType = accountType;
        this.balance = balance;
        this.openedDate = openedDate;
    }

    public Long getId()             { return id; }
    public String getAccountNumber() { return accountNumber; }
    public String getHolderName()   { return holderName; }
    public String getAccountType()  { return accountType; }
    public BigDecimal getBalance()  { return balance; }
    public LocalDate getOpenedDate(){ return openedDate; }

    // Only balance needs a setter — it is the only field we update after creation.
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
