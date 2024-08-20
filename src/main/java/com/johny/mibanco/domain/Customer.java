package com.johny.mibanco.domain;


import com.johny.mibanco.domain.exceptions.CustomerAccountNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class Customer implements AggregateRoot {
    private final UUID id;
    private String name;
    private String address;
    private String phone;
    private final List<Account> accounts;

    public Customer(UUID id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        accounts = new ArrayList<>();
    }

    public void updateInformation(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Account getAccount(long id) {
        Optional<Account> optionalAccount = accounts.stream().filter(account -> account.getNumber() == id).findFirst();
        return optionalAccount.orElseThrow(CustomerAccountNotFoundException::new);
    }

    public void deleteAccount(long id) {
        Account account = getAccount(id);
        accounts.remove(account);
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public void createNewAccount() {
        accounts.add(new Account());
    }

    public void makeAccountDeposit(Long accountNumber, double amount) {
        Optional<Account> possibleAccount = this.accounts.stream().filter(a -> a.getNumber() == accountNumber)
                .findFirst();
        if (possibleAccount.isPresent()) {
            Account account = possibleAccount.get();
            account.deposit(amount);
        }else {
            throw new CustomerAccountNotFoundException();
        }
    }

    public void makeAccountWithdraw(Long accountNumber, double amount) {
        Optional<Account> possibleAccount = this.accounts.stream().filter(a -> a.getNumber() == accountNumber)
                .findFirst();
        if (possibleAccount.isPresent()) {
            Account account = possibleAccount.get();
            account.withdraw(amount);
        }
        else {
            throw new CustomerAccountNotFoundException();
        }
    }

    public List<AccountReport> generateReportByDateRange(Date startDate, Date endDate) {
        return accounts.stream().map(account -> account.generateReport(startDate, endDate)).collect(Collectors.toList());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
