package com.employeeservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author Raitses Vadim
 */


@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EntityAlreadyExistsException extends Exception {

    public EntityAlreadyExistsException(String message) {
        super(message);
    }


}
