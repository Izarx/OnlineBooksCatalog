package com.softserveinc.booklibrary.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
// todo: What this class do?
public class GlobalExceptionHandler {

	@ExceptionHandler({NotValidIdException.class})
	public ResponseEntity<Object> handleNotValidIdException() {
		return null;
	}

	@ExceptionHandler({NotValidEntityException.class})
	public ResponseEntity<Object> handleNotValidEntityException() {
		return null;
	}

}
