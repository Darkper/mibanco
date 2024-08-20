package com.johny.mibanco.infraestructrure.error;


import com.johny.mibanco.domain.exceptions.CustomerAccountNotFoundException;
import com.johny.mibanco.domain.exceptions.CustomerNotFoundException;
import com.johny.mibanco.domain.exceptions.InsufficientBalanceException;
import com.johny.mibanco.domain.exceptions.NegativeAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.ConcurrentHashMap;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    
    private static final Logger LOGGER_ERROR = LoggerFactory.getLogger(ErrorHandler.class);

    private static final String AN_ERROR_OCCURRED_PLEASE_CONTACT_THE_ADMINISTRATOR = "Ocurrió un error favor contactar al administrador.";

    private static final ConcurrentHashMap<String, Integer> STATUS_CODES = new ConcurrentHashMap<>();

    public ErrorHandler() {
        STATUS_CODES.put(InsufficientBalanceException.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        STATUS_CODES.put(NegativeAmountException.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        STATUS_CODES.put(CustomerAccountNotFoundException.class.getSimpleName(), HttpStatus.NOT_FOUND.value());
        STATUS_CODES.put(CustomerNotFoundException.class.getSimpleName(), HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Error> handleAllExceptions(Exception exception) {
        ResponseEntity<Error> result;

        String exceptionName = exception.getClass().getSimpleName();
        String message = exception.getMessage();
        Integer code = STATUS_CODES.get(exceptionName);

        if (code != null) {
            Error error = new Error(exceptionName, message);
            result = new ResponseEntity<>(error, HttpStatus.valueOf(code));
        } else {
            LOGGER_ERROR.error(exceptionName, exception);
            Error error = new Error(exceptionName, AN_ERROR_OCCURRED_PLEASE_CONTACT_THE_ADMINISTRATOR);
            result = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
    }
    
    
    
}