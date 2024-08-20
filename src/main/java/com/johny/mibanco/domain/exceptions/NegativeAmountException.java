package com.johny.mibanco.domain.exceptions;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException() {
        super("Amount must be greater than zero");
    }
}
