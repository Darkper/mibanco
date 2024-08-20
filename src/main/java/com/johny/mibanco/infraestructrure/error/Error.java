package com.johny.mibanco.infraestructrure.error;

public class Error {
    
    private final String exceptionName;
    private final String mensaje;
    
    public Error(String exceptionName, String message) {
        this.exceptionName = exceptionName;
        this.mensaje = message;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public String getMessage() {
        return mensaje;
    }

}
