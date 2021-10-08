package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

    private static Logger logger = LoggerFactory.getLogger(AuthorRepositoryImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public AuthorRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public Author save(Author author) {
        try(Session session = sessionFactory.openSession()) {
            session.save(author);
        }
        return author;
    }

    @Transactional
    @Override
    public Author getById(Integer id) {
        logger.info("Author Repository get id method");
        Author author = null;
        try(Session session = sessionFactory.openSession()){
            author = session.get(Author.class, id);
        }
        return author;
    }

    @Transactional
    @Override
    public void delete(Integer id) {

    }
}
