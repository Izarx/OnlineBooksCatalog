package com.softserveinc.booklibrary.backend.service;

import java.io.Serializable;

import com.softserveinc.booklibrary.backend.entity.Book;

public interface BookService extends EntityService<Book> {

	Book getByIdWithAuthors(Serializable id);
}
