package com.johny.mibanco.infraestructrure.repositories;


import com.johny.mibanco.domain.Account;
import com.johny.mibanco.domain.Customer;
import com.johny.mibanco.domain.Transaction;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class JpaCustomer {
    @Id
    private UUID id;
    private String name;
    private String address;
    private String phone;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaAccount> accounts;

    protected JpaCustomer(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.address = customer.getAddress();
        this.phone = customer.getPhone();
        this.accounts = new ArrayList<>();
        customer.getAccounts()
                .forEach(account -> this.accounts.add(new JpaAccount(account, this)));
    }

    public Customer toCustomer() {
        Customer customer = new Customer(id, name, address, phone);
        accounts.forEach(jpaAccount -> {
            Account account = new Account(jpaAccount.getNumber(), jpaAccount.getBalance());
            jpaAccount.getTransactions().stream().map(jpaTransaction ->
                    new Transaction(jpaTransaction.getId(), jpaTransaction.getTransactionType(), jpaTransaction.getDate(), jpaTransaction.getAmount())).forEach(account::addTransaction);
            customer.addAccount(account);
        });
        return customer;
    }


}
