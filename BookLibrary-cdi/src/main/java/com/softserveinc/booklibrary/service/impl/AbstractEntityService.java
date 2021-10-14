package com.softserveinc.booklibrary.service.impl;

import javax.persistence.EntityNotFoundException;

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
	public T create(T entity) throws IllegalAccessException {
		return repository.create(entity);
	}

	@Override
	public T getById(Integer id) {
		T entity = repository.getById(id);
		if (entity == null) {
			throw new EntityNotFoundException();    // later must create custom exception
		}
		return entity;
	}

	@Override
	@Transactional
	public T update(T entity) throws IllegalAccessException {
		return repository.update(entity);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		repository.delete(id);
	}
}
