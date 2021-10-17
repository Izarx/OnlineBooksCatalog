package com.softserveinc.booklibrary.dao.impl;

import javax.persistence.EntityNotFoundException;

import com.softserveinc.booklibrary.dao.BookRepository;
import com.softserveinc.booklibrary.entity.Book;
import com.softserveinc.booklibrary.exception.NotValidIdValueException;
import com.softserveinc.booklibrary.exception.NullEntityException;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends AbstractEntityRepository<Book> implements BookRepository {

	@Override
	public Book create(Book book) {
		if (book == null) {
			throw new NullEntityException();
		}
		Integer id = book.getBookId();
		if (id != null) {
			throw new NotValidIdValueException(id);
		}
		entityManager.persist(book);
		return book;
	}

	@Override
	public Book update(Book book) {
		if (book == null) {
			throw new NullEntityException();
		}
		Integer id = book.getBookId();
		if (id == null || id <= 0) {
			throw new NotValidIdValueException(id);
		}
		if (entityManager.find(Book.class, id) == null) {
			throw new EntityNotFoundException(book.getClass() + " with ID = " + id + " not found!");
		}
		return entityManager.merge(book);
	}
}
