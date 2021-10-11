package com.softserveinc.booklibrary.dao;

public interface EntityRepository<T> {

	T save(T entity);

	T getById(Integer id);

	void delete(T entity);

}
