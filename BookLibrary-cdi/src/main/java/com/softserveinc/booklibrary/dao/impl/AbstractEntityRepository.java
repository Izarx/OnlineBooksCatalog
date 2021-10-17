package com.softserveinc.booklibrary.dao.impl;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import com.softserveinc.booklibrary.dao.EntityRepository;
import com.softserveinc.booklibrary.entity.Author;
import com.softserveinc.booklibrary.exception.NotValidIdValueException;
import com.softserveinc.booklibrary.exception.NullEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractEntityRepository<T, K> implements EntityRepository<T, K> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityRepository.class);

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public T create(T entity) {
		if (entity == null) {
			throw new NullEntityException();
		}
		K id = getEntityId(entity);
		if (id != null) {
			throw new NotValidIdValueException(id);
		}
		entityManager.persist(entity);
		return entity;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public T update(T entity) {
		if (entity == null) {
			throw new NullEntityException();
		}
		K id = getEntityId(entity);
		if (id == null) {
			throw new NotValidIdValueException(id);
		}
		if (entityManager.find(Author.class, id) == null) {
			throw new EntityNotFoundException(entity.getClass() + " with ID = " + id + " not found!");
		}
		return entityManager.merge(entity);
	}

	@Override
	public T getById(K id) {
		if (id == null) {
			throw new NotValidIdValueException(id);
		}
		return entityManager.find(getGenericClass(), id);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public boolean delete(K id) {
		if (id == null) {
			throw new NotValidIdValueException(id);
		}
		T entity = entityManager.find(getGenericClass(), id);
		if (entity == null) {
			return false;
		}
		entityManager.remove(entity);
		return true;
	}

	private Class<T> getGenericClass() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public abstract K getEntityId(T entity);
}
