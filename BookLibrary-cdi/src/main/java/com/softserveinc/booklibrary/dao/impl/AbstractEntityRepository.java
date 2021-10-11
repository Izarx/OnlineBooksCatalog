package com.softserveinc.booklibrary.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.softserveinc.booklibrary.dao.EntityRepository;

public abstract class AbstractEntityRepository<T> implements EntityRepository<T> {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	@Transactional
	public T save(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public abstract T getById(Integer id);

	@Override
	@Transactional
	public void delete(T entity) {
		entityManager.remove(entity);
	}
}
