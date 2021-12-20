package com.softserveinc.booklibrary.backend.repository;

import java.io.Serializable;
import java.util.List;

import com.softserveinc.booklibrary.backend.entity.AbstractEntity;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.ResponseData;

// todo: need to remove second generic class in declaration
public interface EntityRepository<T extends AbstractEntity<? extends Serializable>, V> {

	T create(T entity);

	T update(T entity);

	T getById(Serializable id);

	boolean delete(Serializable id);

	boolean isEntityValid(T entity);

	boolean isEntityValidForDelete(T entity);

	List<T> getAll();

	ResponseData<T> listEntities(RequestOptions<V> requestOptions);

	List<T> bulkDeleteEntities(List<Serializable> entitiesIdsForDelete);
}
