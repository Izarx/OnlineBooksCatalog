package com.softserveinc.booklibrary.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.Id;
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
	public T create(T entity) throws IllegalAccessException {
		if (entity != null && isIdFieldEmpty(entity)) {
			entityManager.persist(entity);
			return entity;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public T update(T entity) throws IllegalAccessException {
		if (entity != null && !isIdFieldEmpty(entity)) {
			entityManager.merge(entity);
			return entity;
		}
		return null;
	}

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

	private Class<T> getGenericClass() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	private boolean isIdFieldEmpty(T entity) throws IllegalAccessException {
		Field field = Arrays.stream(entity.getClass().getDeclaredFields())
				.filter(f -> f.isAnnotationPresent(Id.class))
				.findFirst().orElse(null);
		if (field != null) {
			field.setAccessible(true);
			return field.get(entity) == null;
		}
		return true;
	}
}
