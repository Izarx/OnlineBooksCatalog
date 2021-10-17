package com.softserveinc.booklibrary.service;

public interface EntityService<T, K> {
	T create(T entity);

	T update(T entity);

	T getById(K id);

	boolean delete(K id);

}
