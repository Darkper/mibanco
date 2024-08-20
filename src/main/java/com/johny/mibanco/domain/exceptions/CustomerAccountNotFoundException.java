package com.johny.mibanco.domain.exceptions;

public class CustomerAccountNotFoundException extends RuntimeException {
    public CustomerAccountNotFoundException() {
        super("Customer account not found");
    }
}
