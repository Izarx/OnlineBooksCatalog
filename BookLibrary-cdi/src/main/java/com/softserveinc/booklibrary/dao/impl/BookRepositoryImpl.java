package com.softserveinc.booklibrary.dao.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import com.softserveinc.booklibrary.dao.BookRepository;
import com.softserveinc.booklibrary.entity.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends EntityRepositoryImpl<Book> implements BookRepository {

	@Override
	@Transactional
	public Optional<Book> getById(Integer id) {
		Book book = getEntityManager().find(Book.class, id);
		return Optional.of(book);
	}
}
