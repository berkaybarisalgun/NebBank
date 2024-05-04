package com.nebbank.customermanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class DeletedUserException extends ApplicationException {

    public DeletedUserException(String message) {
        super(message,HttpStatus.METHOD_NOT_ALLOWED);
    }
}
