package com.johny.mibanco.application.commands;

import lombok.Data;

@Data
public class CreateNewCustomerWithAccountCommand {
    private String name;
    private String address;
    private String phone;
}
