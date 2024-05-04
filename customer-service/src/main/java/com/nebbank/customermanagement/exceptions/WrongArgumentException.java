package com.nebbank.customermanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class WrongArgumentException extends ApplicationException {

    public WrongArgumentException(String message){
        super(message,HttpStatus.METHOD_NOT_ALLOWED);
    }

}
