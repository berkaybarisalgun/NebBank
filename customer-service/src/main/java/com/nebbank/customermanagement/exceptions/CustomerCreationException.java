package com.nebbank.customermanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomerCreationException extends Exception {

    public CustomerCreationException(String message) {
        super(message);
    }


}
