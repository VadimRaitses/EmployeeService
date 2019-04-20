package com.employeeservice.exceptions;

import com.employeeservice.models.ErrorDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * @author Raitses Vadim
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = getErrorDetails(request, ex.getLocalizedMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> entityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = getErrorDetails(request, ex.getLocalizedMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<?> entityAlreadyExistsException(EntityAlreadyExistsException ex, WebRequest request) {
        ErrorDetails errorDetails = getErrorDetails(request, ex.getLocalizedMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(BadEntityException.class)
    public ResponseEntity<?> badEntityException(BadEntityException ex, WebRequest request) {
        ErrorDetails errorDetails = getErrorDetails(request, ex.getLocalizedMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globeExceptionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = getErrorDetails(request, ex.getLocalizedMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ErrorDetails getErrorDetails(WebRequest request, String localizedMessage) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), localizedMessage, request.getDescription(false));
        logger.error(errorDetails.getDetails());
        return errorDetails;
    }


}