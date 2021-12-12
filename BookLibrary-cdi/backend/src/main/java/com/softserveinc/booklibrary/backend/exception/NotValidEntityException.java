package com.softserveinc.booklibrary.backend.exception;

public class NotValidEntityException extends RuntimeException {

	public NotValidEntityException(Object obj) {
		super(String.format("Entity %s is not valid!", obj));
	}
}
