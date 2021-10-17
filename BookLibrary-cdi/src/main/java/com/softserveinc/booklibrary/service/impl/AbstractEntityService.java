package com.softserveinc.booklibrary.service.impl;

import com.softserveinc.booklibrary.dao.EntityRepository;
import com.softserveinc.booklibrary.exception.NotValidIdValueException;
import com.softserveinc.booklibrary.exception.NullEntityException;
import com.softserveinc.booklibrary.service.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractEntityService<T, K> implements EntityService<T, K> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityService.class);
	protected EntityRepository<T, K> repository;

	@Override
	@Transactional
	public T create(T entity) {
		if (entity == null) {
			throw new NullEntityException();
		}
		K id = repository.getEntityId(entity);
		if (id != null) {
			throw new NotValidIdValueException(id);
		}
		return repository.create(entity);
	}

	@Override
	@Transactional
	public T update(T entity) {
		if (entity == null) {
			throw new NullEntityException();
		}
		K id = repository.getEntityId(entity);
		if (id == null) {
			throw new NotValidIdValueException(id);
		}
		return repository.update(entity);
	}

	@Override
	public T getById(K id) {
		if (id == null) {
			throw new NotValidIdValueException(id);
		}
		return repository.getById(id);
	}

	@Override
	@Transactional
	public boolean delete(K id) {
		if (id == null) {
			throw new NotValidIdValueException(id);
		}
		return repository.delete(id);
	}
}
