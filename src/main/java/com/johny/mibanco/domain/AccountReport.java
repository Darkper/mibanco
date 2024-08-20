package com.johny.mibanco.domain;

public class AccountReport {
    private final long accountNumber;
    private final double balance;
    private final double totalDebits;
    private final double totalCredits;

    public AccountReport(long accountNumber, double balance, double totalDebits, double totalCredits) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.totalDebits = totalDebits;
        this.totalCredits = totalCredits;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public double getTotalDebits() {
        return totalDebits;
    }

    public double getTotalCredits() {
        return totalCredits;
    }
}
