package com.softserveinc.booklibrary.dao.impl;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.softserveinc.booklibrary.dao.EntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractEntityRepository<T> implements EntityRepository<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityRepository.class);

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public abstract T create(T entity);

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public abstract T update(T entity);

	@Override
	public T getById(Integer id) {
		if (id != null) {
			return entityManager.find(getGenericClass(), id);
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public boolean delete(Integer id) {
		T entity = null;
		if (id != null) {
			entity = entityManager.find(getGenericClass(), id);
		}
		if (entity != null) {
			entityManager.remove(entity);
			return true;
		}
		return false;
	}

	public Class<T> getGenericClass() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
