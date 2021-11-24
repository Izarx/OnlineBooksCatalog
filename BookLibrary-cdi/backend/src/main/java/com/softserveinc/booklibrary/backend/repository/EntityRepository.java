package com.softserveinc.booklibrary.backend.repository;

import java.io.Serializable;
import java.util.List;

import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.ResponseData;
import com.softserveinc.booklibrary.backend.entity.AbstractEntity;

public interface EntityRepository<T extends AbstractEntity<? extends Serializable>> {

	T create(T entity);

	T update(T entity);

	T getById(Serializable id);

	boolean delete(Serializable id);

	boolean isEntityValid(T entity);

	List<T> getAll();

	ResponseData<T> listEntities(RequestOptions requestOptions);
}
