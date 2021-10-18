package com.softserveinc.booklibrary.dao;

public interface EntityRepository<T, K> {

	T create(T entity);

	T update(T entity);

	T getById(K id);

	boolean delete(K id);

	boolean isEntityValid(T entity);

}
