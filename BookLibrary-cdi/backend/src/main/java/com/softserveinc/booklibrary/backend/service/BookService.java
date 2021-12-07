package com.softserveinc.booklibrary.backend.service;

import java.io.Serializable;

import com.softserveinc.booklibrary.backend.dto.filtering.BookFilter;
import com.softserveinc.booklibrary.backend.entity.Book;

public interface BookService extends EntityService<Book, BookFilter> {

	Book getByIdWithAuthors(Serializable id);
}
