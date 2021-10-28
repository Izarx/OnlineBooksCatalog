package com.softserveinc.booklibrary.backend.dto;

import java.io.Serializable;

import com.softserveinc.booklibrary.backend.entity.MyAppEntity;

public interface MyAppDto<T extends MyAppEntity<? extends Serializable>> {
	T convertDtoToEntity();
}
