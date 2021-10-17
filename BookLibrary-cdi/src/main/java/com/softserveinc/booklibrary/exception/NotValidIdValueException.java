package com.softserveinc.booklibrary.exception;

public class NotValidIdValueException extends RuntimeException {

	public NotValidIdValueException(Integer id) {
		super("Value ID = " + id + " is not valid in this case!");
	}
}
