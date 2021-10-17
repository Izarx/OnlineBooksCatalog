package com.softserveinc.booklibrary.service;

public interface EntityService<T> {
	T create(T entity) throws IllegalAccessException;

	T getById(Integer id);

	T update(T entity) throws IllegalAccessException;

	boolean delete(Integer id);

}
