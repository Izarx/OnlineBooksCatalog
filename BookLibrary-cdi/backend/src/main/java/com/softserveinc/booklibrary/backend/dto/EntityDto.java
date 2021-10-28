package com.softserveinc.booklibrary.backend.dto;

public interface EntityDto<T> {
	T convertDtoToEntity();
}
