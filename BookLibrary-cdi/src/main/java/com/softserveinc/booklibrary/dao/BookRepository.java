package com.softserveinc.booklibrary.dao;

import com.softserveinc.booklibrary.entity.Book;

public interface BookRepository {

    public Book save(Book author);
    public Book getById(Integer id);
    public void delete(Integer id);

}
