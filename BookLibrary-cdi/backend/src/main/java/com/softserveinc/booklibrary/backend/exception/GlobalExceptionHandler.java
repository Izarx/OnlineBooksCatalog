package com.softserveinc.booklibrary.backend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
// todo: What this class do?
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({NotValidIdException.class})
	public void handleNotValidIdException(Exception ex) {
		LOGGER.error(ex.getMessage());
	}

	@ExceptionHandler({NotValidEntityException.class})
	public void handleNotValidEntityException(Exception ex) {
		LOGGER.error(ex.getMessage());
	}

}
