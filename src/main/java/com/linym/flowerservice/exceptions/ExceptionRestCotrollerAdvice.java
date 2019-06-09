package com.linym.flowerservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionRestCotrollerAdvice {
	
	@ExceptionHandler(UserDocNotFoundException.class)
	public final ResponseEntity<UserDocApiError> handleNotFoundException(UserDocNotFoundException ex) {
		  UserDocApiError apiError = new UserDocApiError(HttpStatus.NOT_FOUND);
	       apiError.setMessage(ex.getMessage());
	       return new ResponseEntity<UserDocApiError>(apiError, apiError.getStatus());
		
	}
  
	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<UserDocApiError> handleAllException(RuntimeException ex) {
		UserDocApiError apiError = new UserDocApiError(HttpStatus.INTERNAL_SERVER_ERROR);
	       apiError.setMessage(ex.getMessage());
		return new ResponseEntity<UserDocApiError>(apiError, apiError.getStatus());
		
	}
}
