package com.softserveinc.booklibrary.backend.dto;

import java.io.Serializable;

import com.softserveinc.booklibrary.backend.entity.AbstractEntity;

public interface MyAppDto<T extends AbstractEntity<? extends Serializable>> {
	T convertDtoToEntity();
}
