package com.softserveinc.booklibrary.service.impl;

import com.softserveinc.booklibrary.dao.BookRepository;
import com.softserveinc.booklibrary.entity.Book;
import com.softserveinc.booklibrary.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book create(Book book) {
        return bookRepository.save(book).orElseThrow();
    }

    @Override
    public Book getById(Integer id) {
        logger.info("******************Service method getById****************");
        return bookRepository.getById(id).orElseThrow();
    }

    @Override
    public Book update(Book book) {
        bookRepository.getById(book.getBookId())
                .ifPresent(bookRepository::save);
        return book;
    }

    @Override
    public void delete(Integer id) {
        bookRepository.delete(getById(id));
    }
}
