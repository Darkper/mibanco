package com.johny.mibanco.application.commands;

import lombok.Data;

import java.util.UUID;

@Data
public class MakeAccountTransactionCommand {
    private UUID customerId;
    private long accountId;
    private double amount;
}
