package com.eugene.flight.advice;

import com.eugene.flight.domain.response.ApiResponse;
import com.eugene.flight.exception.AirCompanyNotFoundException;
import com.eugene.flight.exception.AirplaneNotFoundException;
import com.eugene.flight.exception.FlightNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Log4j2
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AirCompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleAirCompanyNotFoundException(AirCompanyNotFoundException ex, WebRequest request) {
        log.error("Failed to find a company", ex);
        return new ApiResponse(ex.getMessage(), false, resolvePathFromRequest(request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = AirplaneNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleAirplaneNotFoundException(AirplaneNotFoundException ex, WebRequest request) {
        log.error("Failed to find an airplane", ex);
        return new ApiResponse(ex.getMessage(), false, resolvePathFromRequest(request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FlightNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleFlightNotFoundException(FlightNotFoundException ex, WebRequest request) {
        log.error("Failed to find a flight", ex);
        return new ApiResponse(ex.getMessage(), false, resolvePathFromRequest(request), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Unable to handle method arguments", ex);
        return buildResponseEntity(new ApiResponse(ex.getMessage(), false, resolvePathFromRequest(request), HttpStatus.BAD_REQUEST));
    }

    private String resolvePathFromRequest(WebRequest request) {
        try {
            return ((ServletWebRequest) request).getRequest()
                    .getAttribute("javax.servlet.forward.request_uri")
                    .toString();
        } catch (Exception e) {
            return "%s".formatted(e.getMessage());
        }
    }

    private ResponseEntity<Object> buildResponseEntity(ApiResponse response) {
        return new ResponseEntity<>(response, response.getStatus());
    }
}
