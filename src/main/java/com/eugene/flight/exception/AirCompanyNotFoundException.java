package com.eugene.flight.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
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
