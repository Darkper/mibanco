package com.johny.mibanco.application.commands;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateCustomerCommand {
    private UUID customerId;
    private String name;
    private String address;
    private String phone;
}
