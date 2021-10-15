package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.BookRepository;
import com.softserveinc.booklibrary.entity.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends AbstractEntityRepository<Book> implements BookRepository {

	@Override
	public Book create(Book book) {
		if (book != null && book.getBookId() == null) {
			entityManager.persist(book);
			return book;
		}
		return null;
	}

	@Override
	public Book update(Book book) {
		if (book != null && book.getBookId() != null) {
			entityManager.merge(book);
			return book;
		}
		return null;
	}
}
