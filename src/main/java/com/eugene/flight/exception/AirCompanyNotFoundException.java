package com.eugene.flight.exception;

public class AirCompanyNotFoundException extends RuntimeException{

    public AirCompanyNotFoundException(String message) {
        super(message);
    }

    public AirCompanyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AirCompanyNotFoundException() {

    }
}
