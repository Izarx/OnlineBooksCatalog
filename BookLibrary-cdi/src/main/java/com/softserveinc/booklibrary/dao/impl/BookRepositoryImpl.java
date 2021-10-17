package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.BookRepository;
import com.softserveinc.booklibrary.entity.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends AbstractEntityRepository<Book, Integer> implements BookRepository {
	@Override
	public Integer getEntityId(Book book) {
		return book.getBookId();
	}
}
