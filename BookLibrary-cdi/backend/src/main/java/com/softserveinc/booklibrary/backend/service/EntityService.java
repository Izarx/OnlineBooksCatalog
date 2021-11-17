package com.softserveinc.booklibrary.backend.service;

import java.io.Serializable;
import java.util.List;

import com.softserveinc.booklibrary.backend.dto.paging.MyPage;
import com.softserveinc.booklibrary.backend.entity.AbstractEntity;

public interface EntityService<T extends AbstractEntity<? extends Serializable>> {

	T create(T entity);

	T update(T entity);

	T getById(Serializable id);

	boolean delete(Serializable id);

	List<T> getAll();

	MyPage<T> getAllPageableAndSortable(int pageId, int numEntitiesOnPage);

}
