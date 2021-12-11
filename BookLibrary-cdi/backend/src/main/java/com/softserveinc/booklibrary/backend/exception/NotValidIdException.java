package com.softserveinc.booklibrary.backend.exception;

public class NotValidIdException extends RuntimeException {

	public NotValidIdException(Object id) {
		super(String.format("ID object %s is not valid in this case!", String.valueOf(id)));
	}
}
