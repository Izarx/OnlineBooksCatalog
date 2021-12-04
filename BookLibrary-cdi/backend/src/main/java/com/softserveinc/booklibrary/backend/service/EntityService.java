package com.softserveinc.booklibrary.backend.service;

import java.io.Serializable;
import java.util.List;

import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.ResponseData;
import com.softserveinc.booklibrary.backend.entity.AbstractEntity;
import org.springframework.transaction.annotation.Transactional;

public interface EntityService<T extends AbstractEntity<? extends Serializable>> {

	T create(T entity);

	T update(T entity);

	T getById(Serializable id);

	boolean delete(Serializable id);

	List<T> getAll();

	ResponseData<T> listEntities(RequestOptions<T> requestOptions);

	List<T> bulkDeleteEntities(List<Serializable> entitiesIdsForDelete);

}
