package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.BookRepository;
import com.softserveinc.booklibrary.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BookRepositoryImpl extends EntityRepositoryImpl<Book> implements BookRepository {

    public BookRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Optional<Book> getById(Integer id) {
        Book book = null;
        try(Session session = getSessionFactory().openSession()){
            book = session.get(Book.class, id);
        }
        return Optional.of(book);
    }
}
