package com.johny.mibanco.domain;

import com.johny.mibanco.domain.exceptions.InsufficientBalanceException;
import com.johny.mibanco.domain.exceptions.NegativeAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = new Account(1L, 1000.0);
    }

    @Test
    void deposit_shouldIncreaseBalance_whenAmountIsPositive() throws NegativeAmountException {
        account.deposit(200.0);
        assertEquals(1200.0, account.getBalance());
        assertEquals(1, account.getTransactions().size());
        assertEquals(TransactionType.DEBIT, account.getTransactions().get(0).getTransactionType());
    }

    @Test
    void deposit_shouldThrowNegativeAmountException_whenAmountIsZeroOrNegative() {
        assertThrows(NegativeAmountException.class, () -> account.deposit(0.0));
        assertThrows(NegativeAmountException.class, () -> account.deposit(-100.0));
    }

    @Test
    void withdraw_shouldDecreaseBalance_whenAmountIsPositiveAndSufficientBalance() throws NegativeAmountException, InsufficientBalanceException {
        account.withdraw(200.0);
        assertEquals(800.0, account.getBalance());
        assertEquals(1, account.getTransactions().size());
        assertEquals(TransactionType.CREDIT, account.getTransactions().get(0).getTransactionType());
    }

    @Test
    void withdraw_shouldThrowNegativeAmountException_whenAmountIsZeroOrNegative() {
        assertThrows(NegativeAmountException.class, () -> account.withdraw(0.0));
        assertThrows(NegativeAmountException.class, () -> account.withdraw(-100.0));
    }

    @Test
    void withdraw_shouldThrowInsufficientBalanceException_whenAmountExceedsBalance() {
        assertThrows(InsufficientBalanceException.class, () -> account.withdraw(2000.0));
    }

    @Test
    void addTransaction_shouldAddTransactionToAccount() {
        Transaction transaction = new Transaction(TransactionType.DEBIT, 200.0);
        account.addTransaction(transaction);
        List<Transaction> transactions = account.getTransactions();
        assertEquals(1, transactions.size());
        assertEquals(transaction, transactions.get(0));
    }

    @Test
    void updateBalance_shouldUpdateBalance() {
        account.updateBalance(500.0);
        assertEquals(500.0, account.getBalance());
    }

    @Test
    void getTransactionsBetweenDates_shouldReturnTransactionsWithinDateRange() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 31);

        Transaction t1 = new Transaction(UUID.randomUUID(), TransactionType.DEBIT, Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), 100.0);
        Transaction t2 = new Transaction(UUID.randomUUID(), TransactionType.CREDIT, Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), 200.0);
        Transaction t3 = new Transaction(UUID.randomUUID(), TransactionType.DEBIT, Date.from(LocalDate.of(2023, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), 150.0);

        account.addTransaction(t1);
        account.addTransaction(t2);
        account.addTransaction(t3);

        List<Transaction> transactions = account.getTransactionsBetweenDates(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        assertEquals(2, transactions.size());
        assertTrue(transactions.contains(t1));
        assertTrue(transactions.contains(t2));
        assertFalse(transactions.contains(t3));
    }

    @Test
    void getTotalDebits_shouldReturnTotalDebitsFromList() {
        Transaction t1 = new Transaction(UUID.randomUUID(), TransactionType.DEBIT, Date.from(LocalDate.of(2023, 1, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()), 100.0);
        Transaction t2 = new Transaction(UUID.randomUUID(), TransactionType.CREDIT, Date.from(LocalDate.of(2023, 1, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()), 200.0);
        Transaction t3 = new Transaction(UUID.randomUUID(), TransactionType.DEBIT, Date.from(LocalDate.of(2023, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), 150.0);

        account.addTransaction(t1);
        account.addTransaction(t2);
        account.addTransaction(t3);

        double totalDebits = account.getTotalDebits(account.getTransactions());

        assertEquals(250.0, totalDebits);
    }

    @Test
    void getTotalCredits_shouldReturnTotalCreditsFromList() {
        Transaction t1 = new Transaction(UUID.randomUUID(), TransactionType.DEBIT, Date.from(LocalDate.of(2023, 1, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()), 100.0);
        Transaction t2 = new Transaction(UUID.randomUUID(), TransactionType.CREDIT, Date.from(LocalDate.of(2023, 1, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()), 200.0);
        Transaction t3 = new Transaction(UUID.randomUUID(), TransactionType.CREDIT, Date.from(LocalDate.of(2023, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), 150.0);

        account.addTransaction(t1);
        account.addTransaction(t2);
        account.addTransaction(t3);

        double totalCredits = account.getTotalCredits(account.getTransactions());

        assertEquals(350.0, totalCredits);
    }

    @Test
    void generateReport_shouldReturnCorrectReport_whenTransactionsExist() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 31);

        Transaction t1 = new Transaction(UUID.randomUUID(), TransactionType.DEBIT, Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), 100.0);
        Transaction t2 = new Transaction(UUID.randomUUID(), TransactionType.CREDIT, Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), 200.0);
        Transaction t3 = new Transaction(UUID.randomUUID(), TransactionType.DEBIT, Date.from(LocalDate.of(2023, 2, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), 150.0);

        account.addTransaction(t1);
        account.addTransaction(t2);
        account.addTransaction(t3);

        AccountReport report = account.generateReport(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        assertEquals(1000.0, report.getBalance()); // Assuming balance is not affected
        assertEquals(100.0, report.getTotalDebits());
        assertEquals(200.0, report.getTotalCredits());
    }
}
