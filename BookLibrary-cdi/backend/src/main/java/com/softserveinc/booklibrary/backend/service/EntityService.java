package com.softserveinc.booklibrary.backend.service;

import java.io.Serializable;

import com.softserveinc.booklibrary.backend.entity.MyAppEntity;

public interface EntityService<T extends MyAppEntity<? extends Serializable>> {

	T create(T entity);

	T update(T entity);

	T getById(Serializable id);

	boolean delete(Serializable id);

}
