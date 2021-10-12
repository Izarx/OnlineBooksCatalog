package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.BookRepository;
import com.softserveinc.booklibrary.entity.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryAbstract extends AbstractEntityRepository<Book> implements BookRepository {

	@Override
	public Book getById(Integer id) {
		//TODO no need for temporal variable
		Book book = entityManager.find(Book.class, id);
		return book;
	}
}
