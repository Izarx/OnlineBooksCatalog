package com.softserveinc.booklibrary.exception;

public class NotValidIdValueException extends RuntimeException{
	private static final long serialVersionUID = 2344686998228668595L;

	public NotValidIdValueException(Integer id) {
		super("Value ID = " + id + " is not valid in this case!");
	}
}
