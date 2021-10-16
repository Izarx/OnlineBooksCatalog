package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.BookRepository;
import com.softserveinc.booklibrary.entity.Book;
import com.softserveinc.booklibrary.exception.NotValidIdValueException;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends AbstractEntityRepository<Book> implements BookRepository {

	@Override
	public Book create(Book book) {
		if (book != null) {
			Integer id = book.getBookId();
			if (id != null) {
				throw new NotValidIdValueException(id);
			}
			entityManager.persist(book);
			return book;
		}
		return null;
	}

	@Override
	public Book update(Book book) {
		if (book != null) {
			Integer id = book.getBookId();
			if (id == null || id <= 0) {
				throw new NotValidIdValueException(id);
			}
			entityManager.merge(book);
			return book;
		}
		return null;
	}
}
