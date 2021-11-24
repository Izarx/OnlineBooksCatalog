package com.softserveinc.booklibrary.backend.service;

import java.io.Serializable;
import java.util.List;

import com.softserveinc.booklibrary.backend.dto.paging.ApplicationRequestPage;
import com.softserveinc.booklibrary.backend.dto.paging.ApplicationResponsePage;
import com.softserveinc.booklibrary.backend.entity.AbstractEntity;

public interface EntityService<T extends AbstractEntity<? extends Serializable>> {

	T create(T entity);

	T update(T entity);

	T getById(Serializable id);

	boolean delete(Serializable id);

	List<T> getAll();

	ApplicationResponsePage<T> listEntities(ApplicationRequestPage applicationRequestPage);

}
