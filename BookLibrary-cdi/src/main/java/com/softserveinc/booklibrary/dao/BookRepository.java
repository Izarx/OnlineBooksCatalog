package com.softserveinc.booklibrary.dao;

import com.softserveinc.booklibrary.entity.Book;

import java.util.Optional;

public interface BookRepository {

    public Optional<Book> save(Book author);
    public Optional<Book> getById(Integer id);
    public void delete(Book book);

}
