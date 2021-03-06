package com.employeeservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author Raitses Vadim
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BadEntityException extends Exception {

    public BadEntityException(String message) {
        super(message);
    }


}
