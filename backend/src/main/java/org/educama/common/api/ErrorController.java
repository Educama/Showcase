package org.educama.common.api;

import org.educama.common.exceptions.PreconditionViolationException;
import org.educama.common.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * The ErrorController catches all of the exceptions to create a standard HTTP Rest Error to the caller
 * Either in the Error Controller or in the client internationalization of error messages should be handled.
 */
@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        ResponseEntity<String> response = new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return response;
    }

    @ExceptionHandler(value = {PreconditionViolationException.class})
    protected ResponseEntity<String> handlePreconditionViolationException(PreconditionViolationException ex, WebRequest request) {
        ResponseEntity<String> response = new ResponseEntity<String>(ex.getMessage(), HttpStatus.PRECONDITION_FAILED);
        return response;
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ResponseEntity<String> response = new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
        return response;
    }

}
