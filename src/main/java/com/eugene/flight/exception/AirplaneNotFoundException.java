package com.eugene.flight.exception;

public class AirplaneNotFoundException extends RuntimeException {

    public AirplaneNotFoundException(String message) {
        super(message);
    }

    public AirplaneNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
