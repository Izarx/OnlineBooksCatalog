package com.softserveinc.booklibrary.backend.repository.impl;

import java.time.LocalDate;

import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.repository.BookRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends AbstractEntityRepository<Book> implements BookRepository {

	@Override
	public boolean isEntityValid(Book book) {
		String name = book.getName();
		Integer yearPublished = book.getYearPublished();
		String publisher = book.getPublisher();
		if (name == null || name.length() > Book.NAME_LENGTH) {
			return false;
		}
		if (yearPublished == null || yearPublished < 0 || yearPublished > LocalDate.now().getYear()) {
			return false;
		}
		return publisher != null && publisher.length() <= Book.PUBLISHER_LENGTH;
	}
}
