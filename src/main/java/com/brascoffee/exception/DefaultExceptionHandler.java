package com.brascoffee.exception;

import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.brascoffee.models.ApiErrorResponse;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ BadCredentialsException.class })
	public ResponseEntity<Object> handleBadCredentialsException(Exception ex, WebRequest request) {
		String errorDescription = ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : ex.toString();
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(errorDescription);

		return new ResponseEntity<>(apiErrorResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
	}
	
	@Override
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		//ApiErrorMessage apiErrorMessage = new ApiErrorMessage("Invalid arguments");
		Map<String,String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
		
		if(ex instanceof ConstraintViolationException) {
			ApiErrorResponse apiErrorResponse = new ApiErrorResponse(ex.getMessage());

			return new ResponseEntity<>(apiErrorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

		String errorDescription = ex.getLocalizedMessage() != null ? ex.getLocalizedMessage() : ex.toString();
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse("There was an error in your request");

		return new ResponseEntity<>(apiErrorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

}
