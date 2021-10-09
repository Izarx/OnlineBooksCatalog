package com.softserveinc.booklibrary.service;

import com.softserveinc.booklibrary.entity.Book;

public interface BookService {

    public Book create(Book book);
    public Book getById(Integer id);
    public Book update(Book book);
    public void delete(Integer id);
}
