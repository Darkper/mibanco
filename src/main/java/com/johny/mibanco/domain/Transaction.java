package com.johny.mibanco.domain;


import java.util.Date;
import java.util.UUID;

public class Transaction {
    private final UUID id;
    private final TransactionType transactionType;
    private final Date date;
    private final double amount;

    public Transaction(UUID id, TransactionType transactionType, Date date, double amount) {
        this.id = id;
        this.transactionType = transactionType;
        this.date = date;
        this.amount = amount;
    }

    protected Transaction(TransactionType transactionType, double amount) {
        this.id = UUID.randomUUID();
        this.transactionType = transactionType;
        this.date = new Date();
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}
