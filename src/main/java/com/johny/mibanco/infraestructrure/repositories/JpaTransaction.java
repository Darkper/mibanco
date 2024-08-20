package com.johny.mibanco.infraestructrure.repositories;

import com.johny.mibanco.domain.Transaction;
import com.johny.mibanco.domain.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class JpaTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private TransactionType transactionType;
    private Date date;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private JpaAccount account;

    public JpaTransaction(Transaction transaction, JpaAccount jpaAccount) {
        this.id = transaction.getId();
        this.transactionType = transaction.getTransactionType();
        this.date = transaction.getDate();
        this.amount = transaction.getAmount();
        this.account = jpaAccount;
    }
}
