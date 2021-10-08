package com.softserveinc.booklibrary.dao;

import com.softserveinc.booklibrary.entity.Author;

public interface AuthorRepository {

    public Author save(Author author);
    public Author getById(Integer id);
    public void delete(Integer id);
}