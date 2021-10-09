package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.BookRepository;
import com.softserveinc.booklibrary.entity.Author;
import com.softserveinc.booklibrary.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private static Logger logger = LoggerFactory.getLogger(BookRepositoryImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    @Override
    public Optional<Book> save(Book book) {
        try(Session session = sessionFactory.openSession()) {
            session.save(book);
        }
        return Optional.ofNullable(book);
    }

    @Transactional
    @Override
    public Optional<Book> getById(Integer id) {
        logger.info("Author Repository get id method");
        Book book = null;
        try(Session session = sessionFactory.openSession()){
            book = session.get(Book.class, id);
        }
        return Optional.ofNullable(book);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Optional<Book> book = getById(id);
        try(Session session = sessionFactory.openSession()) {
            book.ifPresent(session::delete);
        }
    }
}
