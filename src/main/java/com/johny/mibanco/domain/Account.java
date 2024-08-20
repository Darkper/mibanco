package com.johny.mibanco.domain;

import com.johny.mibanco.domain.exceptions.InsufficientBalanceException;
import com.johny.mibanco.domain.exceptions.NegativeAmountException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Account {
    private long number;
    private double balance;
    private final List<Transaction> transactions;

    public Account(long number, double balance) {
        this.number = number;
        this.balance = balance;
        transactions = new ArrayList<>();
    }

    public void updateBalance(Double balance) {
        this.balance = balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    protected Account() {
        transactions = new ArrayList<>();
    }

    protected void deposit(double amount) throws NegativeAmountException {
        if (amount <= 0) {
            throw new NegativeAmountException();
        }
        transactions.add(new Transaction(TransactionType.DEBIT, amount));
        this.balance += amount;
    }

    protected void withdraw(double amount) throws NegativeAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new NegativeAmountException();
        }

        if (amount > balance) {
            throw new InsufficientBalanceException();
        }

        transactions.add(new Transaction(TransactionType.CREDIT, amount));
        balance -= amount;
    }

    public AccountReport generateReport(Date startDate, Date endDate) {

        List<Transaction> transactionsBetweenDates = getTransactionsBetweenDates(startDate, endDate);
        if (!transactionsBetweenDates.isEmpty()) {
            double totalCredits = getTotalCredits(transactionsBetweenDates);
            double totalDebits = getTotalDebits(transactionsBetweenDates);

            return new AccountReport(number, balance, totalDebits, totalCredits);
        }
        return new AccountReport(number, balance, 0, 0);
    }

    public List<Transaction> getTransactionsBetweenDates(Date startDate, Date endDate) {
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        return transactions.stream()
                .filter(transaction -> {
                    LocalDate transactionDate = transaction.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    return !transactionDate.isBefore(startLocalDate) && !transactionDate.isAfter(endLocalDate);
                })
                .collect(Collectors.toList());
    }

    public double getTotalDebits(List<Transaction> transactions) {
        return transactions.stream().filter(transaction -> transaction.getTransactionType().equals(TransactionType.DEBIT)).map(Transaction::getAmount).reduce(0.0, Double::sum);
    }

    public double getTotalCredits(List<Transaction> transactions) {
        return transactions.stream().filter(transaction -> transaction.getTransactionType().equals(TransactionType.CREDIT)).map(Transaction::getAmount).reduce(0.0, Double::sum);
    }

    public long getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
