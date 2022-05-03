package com.eugene.flight.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AirplaneNotFoundException extends RuntimeException {

    public AirplaneNotFoundException(String message) {
        super(message);
    }

    public AirplaneNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AirplaneNotFoundException() {
    }
}
