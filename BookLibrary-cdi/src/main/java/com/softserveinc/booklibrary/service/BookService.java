package com.softserveinc.booklibrary.service;

import com.softserveinc.booklibrary.entity.Book;

public interface BookService {

	Book create(Book book);

	Book getById(Integer id);

	Book update(Book book);

	void delete(Integer id);
}
