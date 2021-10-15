package com.softserveinc.booklibrary.service.impl;

import com.softserveinc.booklibrary.dao.EntityRepository;
import com.softserveinc.booklibrary.service.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractEntityService<T> implements EntityService<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityService.class);
	protected EntityRepository<T> repository;

	@Override
	@Transactional
	public T create(T entity) {
		return repository.create(entity);
	}

	@Override
	public T getById(Integer id) {
		return repository.getById(id);
	}

	@Override
	@Transactional
	public T update(T entity) {
		return repository.update(entity);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		repository.delete(id);
	}
}
