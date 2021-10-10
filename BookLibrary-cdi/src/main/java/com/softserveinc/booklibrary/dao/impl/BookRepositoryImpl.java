package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.BookRepository;
import com.softserveinc.booklibrary.entity.Book;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public class BookRepositoryImpl extends EntityRepositoryImpl<Book> implements BookRepository {

    @Override
    @Transactional
    public Optional<Book> getById(Integer id) {
        Book book = getEntityManager().find(Book.class, id);
        return Optional.of(book);
    }
}
