package com.johny.mibanco.domain;

import com.johny.mibanco.domain.exceptions.CustomerAccountNotFoundException;
import com.johny.mibanco.domain.exceptions.InsufficientBalanceException;
import com.johny.mibanco.domain.exceptions.NegativeAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(UUID.randomUUID(), "John Doe", "123 Main St", "555-1234");
    }

    @Test
    void getAccount_shouldReturnAccount_whenAccountExists() {
        Account account = new Account(1L, 1000.0);
        customer.addAccount(account);
        Account retrievedAccount = customer.getAccount(1L);
        assertEquals(account, retrievedAccount);
    }

    @Test
    void getAccount_shouldThrowCustomerAccountNotFoundException_whenAccountDoesNotExist() {
        assertThrows(CustomerAccountNotFoundException.class, () -> customer.getAccount(1L));
    }

    @Test
    void makeAccountDeposit_shouldIncreaseAccountBalance_whenAccountExists() throws NegativeAmountException {
        Account account = new Account(1L, 1000.0);
        customer.addAccount(account);
        customer.makeAccountDeposit(1L, 200.0);
        assertEquals(1200.0, account.getBalance());
    }

    @Test
    void makeAccountDeposit_shouldThrowCustomerAccountNotFoundException_whenAccountDoesNotExist() {
        assertThrows(CustomerAccountNotFoundException.class, () -> customer.makeAccountDeposit(1L, 200.0));
    }

    @Test
    void makeAccountWithdraw_shouldDecreaseAccountBalance_whenAccountExists() throws NegativeAmountException, InsufficientBalanceException {
        Account account = new Account(1L, 1000.0);
        customer.addAccount(account);
        customer.makeAccountWithdraw(1L, 200.0);
        assertEquals(800.0, account.getBalance());
    }

    @Test
    void makeAccountWithdraw_shouldThrowCustomerAccountNotFoundException_whenAccountDoesNotExist() {
        assertThrows(CustomerAccountNotFoundException.class, () -> customer.makeAccountWithdraw(1L, 200.0));
    }

    @Test
    void makeAccountWithdraw_shouldThrowInsufficientBalanceException_whenBalanceIsInsufficient() {
        Account account = new Account(1L, 100.0);
        customer.addAccount(account);
        assertThrows(InsufficientBalanceException.class, () -> customer.makeAccountWithdraw(1L, 200.0));
    }

    @Test
    void updateInformation_shouldUpdateCustomerDetails() {
        customer.updateInformation("Jane Doe", "456 Elm St", "555-5678");
        assertEquals("Jane Doe", customer.getName());
        assertEquals("456 Elm St", customer.getAddress());
        assertEquals("555-5678", customer.getPhone());
    }

    @Test
    void addAccount_shouldAddAccountToCustomer() {
        Account account = new Account(1L, 1000.0);
        customer.addAccount(account);
        assertEquals(1, customer.getAccounts().size());
        assertEquals(account, customer.getAccounts().get(0));
    }

    @Test
    void createNewAccount_shouldAddNewAccountToCustomer() {
        customer.createNewAccount();
        assertEquals(1, customer.getAccounts().size());
    }

    @Test
    void deleteAccount_shouldRemoveAccount_whenAccountExists() {
        Account account1 = new Account(1L, 1000.0);
        Account account2 = new Account(2L, 2000.0);
        customer.addAccount(account1);
        customer.addAccount(account2);

        customer.deleteAccount(1L);

        assertEquals(1, customer.getAccounts().size());
        assertFalse(customer.getAccounts().contains(account1));
        assertTrue(customer.getAccounts().contains(account2));
    }

    @Test
    void deleteAccount_shouldThrowCustomerAccountNotFoundException_whenAccountDoesNotExist() {
        Account account = new Account(1L, 1000.0);
        customer.addAccount(account);

        assertThrows(CustomerAccountNotFoundException.class, () -> customer.deleteAccount(2L));
    }

    @Test
    void deleteAccount_shouldDoNothing_whenDeletingNonExistingAccount() {
        Account account1 = new Account(1L, 1000.0);
        Account account2 = new Account(2L, 2000.0);
        customer.addAccount(account1);
        customer.addAccount(account2);

        assertThrows(CustomerAccountNotFoundException.class, () -> customer.deleteAccount(3L));
        assertEquals(2, customer.getAccounts().size());
        assertTrue(customer.getAccounts().contains(account1));
        assertTrue(customer.getAccounts().contains(account2));
    }

    @Test
    void generateReportByDateRange_shouldFilterTransactionsForEachAccount() {
        Account account1 = mock(Account.class);
        Account account2 = mock(Account.class);

        customer.addAccount(account1);
        customer.addAccount(account2);

        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 31);

        customer.generateReportByDateRange(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        verify(account1, times(1)).generateReport(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        verify(account2, times(1)).generateReport(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()), Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}
