package com.softserveinc.booklibrary.service.impl;

import com.softserveinc.booklibrary.dao.EntityRepository;
import com.softserveinc.booklibrary.service.EntityService;

public class AbstractEntityService<T> implements EntityService<T> {

	protected EntityRepository<T> repository;

	@Override
	public T create(T entity) {
		return repository.create(entity);
	}

	@Override
	public T getById(Integer id) {
		return repository.getById(id);
	}

	@Override
	public T update(T entity) {
		return repository.update(entity);
	}

	@Override
	public boolean delete(Integer id) {
		return repository.delete(id);
	}
}
