package com.softserveinc.booklibrary.backend.exception;

public class NotValidIdException extends RuntimeException {

	public NotValidIdException(Object id) {
		super("ID object " + id + " is not valid in this case!");
	}
}
