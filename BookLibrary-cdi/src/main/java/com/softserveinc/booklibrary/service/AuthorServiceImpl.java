package com.softserveinc.booklibrary.service;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author create(Author author) {
        return null;
    }

    @Override
    public Author getById(Integer id) {
        return null;
    }

    @Override
    public Author update(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }
}
