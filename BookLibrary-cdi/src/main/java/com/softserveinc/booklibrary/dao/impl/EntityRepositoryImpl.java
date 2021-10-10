package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.EntityRepository;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.transaction.Transactional;
import java.util.Optional;

public class EntityRepositoryImpl<T> implements EntityRepository<T> {

    @Getter
    private final SessionFactory sessionFactory;

    public EntityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Optional<T> save(T t) {
        try(Session session = sessionFactory.openSession()) {
            session.save(t);
        }
        return Optional.of(t);
    }

    @Override
    public Optional<T> getById(Integer id) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public void delete(T t) {
        try(Session session = sessionFactory.openSession()) {
            session.delete(t);
        }
    }
}
