package com.nebbank.customermanagement.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class ApplicationException extends RuntimeException {
    private final HttpStatus status;

    public ApplicationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
