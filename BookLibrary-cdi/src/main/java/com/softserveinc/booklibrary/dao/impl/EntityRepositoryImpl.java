package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.EntityRepository;
import lombok.Getter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

public class EntityRepositoryImpl<T> implements EntityRepository<T> {

    @PersistenceContext
    @Getter
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<T> save(T t) {
        entityManager.persist(t);
        return Optional.ofNullable(t);
    }

    @Override
    public Optional<T> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public void delete(T t) {
        entityManager.remove(t);
    }
}
