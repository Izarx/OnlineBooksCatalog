package com.softserveinc.booklibrary.dao;

import com.softserveinc.booklibrary.entity.Author;

import java.util.Optional;

public interface AuthorRepository {

    public Optional<Author> save(Author author);
    public Optional<Author> getById(Integer id);
    public void delete(Author author);
}