package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthorRepositoryImpl extends EntityRepositoryImpl<Author> implements AuthorRepository {

    public AuthorRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Author> getById(Integer id) {
        Author author = null;
        try(Session session = getSessionFactory().openSession()){
            author = session.get(Author.class, id);
        }
        return Optional.of(author);
    }
}
