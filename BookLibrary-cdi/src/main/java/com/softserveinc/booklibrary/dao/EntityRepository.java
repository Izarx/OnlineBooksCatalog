package com.softserveinc.booklibrary.dao;

import com.softserveinc.booklibrary.entity.Author;

import java.util.Optional;

public interface EntityRepository <T>{

    public Optional<T> save(T entity);
    public Optional<T> getById(Integer id);
    public void delete(T entity);

}
