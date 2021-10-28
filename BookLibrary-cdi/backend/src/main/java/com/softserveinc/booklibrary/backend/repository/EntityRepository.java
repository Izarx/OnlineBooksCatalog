package com.softserveinc.booklibrary.backend.repository;

import java.io.Serializable;
import java.util.List;

import com.softserveinc.booklibrary.backend.entity.MyAppEntity;

public interface EntityRepository<T extends MyAppEntity<? extends Serializable>> {

	T create(T entity);

	T update(T entity);

	T getById(Serializable id);

	boolean delete(Serializable id);

	boolean isEntityValid(T entity);

	List<T> getAll();
}
