package com.softserveinc.booklibrary.backend.service;

import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.pagination.filtering.BookFilter;

public interface BookService extends EntityService<Book, BookFilter> {

	Book getByIdWithAuthors(Integer id);
}
