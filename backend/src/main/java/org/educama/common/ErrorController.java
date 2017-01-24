package org.educama.common;

import org.educama.customer.boundary.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
/**
 * The ErrorController catches all of the exceptions to create a standard HTTP Rest Error to the caller
 * Either in the Error Controller or in the client internationalization of error messages should be handled
 */
public class ErrorController {
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
    protected ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        ResponseEntity<String> response = new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return response;
    }
	
	@ExceptionHandler(value = { NullPointerException.class })
    protected ResponseEntity<String> handleNullPointerException(NullPointerException ex, WebRequest request) {
        ResponseEntity<String> response = new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return response;
    }
	
	@ExceptionHandler(value = { EntityExistsException.class })
    protected ResponseEntity<String> handleEntityExistsException(EntityExistsException ex, WebRequest request) {
        ResponseEntity<String> response = new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        return response;
    }

}
