package com.eugene.flight.domain.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public final class ApiResponse {
    private String data;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private final LocalDateTime date;
    private final HttpStatus status;
    private final String cause;
    private final boolean isSuccess;
    private final String path;

    public ApiResponse(String cause, boolean isSuccess, String path, HttpStatus status) {
        this.date = LocalDateTime.now();
        this.cause = cause;
        this.isSuccess = isSuccess;
        this.path = path;
        this.status = status;
    }
}
