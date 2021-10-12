package com.softserveinc.booklibrary.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.softserveinc.booklibrary.dao.EntityRepository;

public abstract class AbstractEntityRepository<T> implements EntityRepository<T> {

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	//TODO javax.transaction?
	@Transactional
	public T create(T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		//TODO ?
		return entity;
	}

	@Override
	public abstract T getById(Integer id);

	@Override
	@Transactional
	public boolean delete(Integer id) {
		//TODO quite tricky
		T entity = getById(id);
		if (entity != null) {
			entityManager.remove(entity);
			return true;
		}
		return false;
	}
}
