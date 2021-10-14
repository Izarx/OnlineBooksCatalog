package com.softserveinc.booklibrary.dao;

public interface EntityRepository<T> {

	T create(T entity) throws IllegalAccessException;

	T update(T entity) throws IllegalAccessException;

	T getById(Integer id);

	boolean delete(Integer id);

}
