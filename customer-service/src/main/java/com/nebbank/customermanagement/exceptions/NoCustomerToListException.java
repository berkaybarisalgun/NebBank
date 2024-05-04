package com.nebbank.customermanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoCustomerToListException extends ApplicationException{
    public NoCustomerToListException(String message, HttpStatus status) {
        super(message, status);
    }
}
