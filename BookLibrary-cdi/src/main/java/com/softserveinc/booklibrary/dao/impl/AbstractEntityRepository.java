package com.softserveinc.booklibrary.dao.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

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
	public T create(T entity) {
		if (entity != null && isIdFieldEmpty(entity)) {
			entityManager.persist(entity);
			return entity;
		}
		return null;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public T update(T entity) {
		if (entity != null && !isIdFieldEmpty(entity)) {
			entityManager.merge(entity);
		}
		return entity;
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
		T entity = entityManager.find(getGenericClass(), id);
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

	private static <T> boolean isIdFieldEmpty(T entity) {
		if (entity != null) {
			for (Field field : entity.getClass().getDeclaredFields()) {
				if (field.isAnnotationPresent(Id.class)) {
					field.setAccessible(true);
					try {
						if (field.get(entity) != null) {
							return false;
						}
					} catch (IllegalAccessException e) {
						LOGGER.warn("Can't get access to field {} in class {}!",
								field.getName(), entity.getClass().getName());
					}
				}
			}
		}
		return true;
	}
}
