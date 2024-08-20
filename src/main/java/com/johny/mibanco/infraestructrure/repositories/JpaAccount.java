package com.johny.mibanco.infraestructrure.repositories;


import com.johny.mibanco.domain.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class JpaAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long number;
    private double balance;

    @ManyToOne
    @JoinColumn(name = "customer_id") // Llave foránea en la tabla account
    private JpaCustomer customer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JpaTransaction> transactions;

    public JpaAccount(Account account, JpaCustomer jpaCustomer) {
        this.number = account.getNumber();
        this.balance = account.getBalance();
        this.customer = jpaCustomer;
        this.transactions = new ArrayList<>();
        account.getTransactions()
                .forEach(transaction -> this.transactions.add(new JpaTransaction(transaction, this)));
    }

}
