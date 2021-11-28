package com.softserveinc.booklibrary.backend.service.impl;

import java.io.Serializable;

import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.ResponseData;
import com.softserveinc.booklibrary.backend.repository.BookRepository;
import com.softserveinc.booklibrary.backend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl extends AbstractEntityService<Book> implements BookService {

	@Autowired
	public BookServiceImpl(BookRepository bookRepository) {
		repository = bookRepository;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ResponseData<Book> listEntities(RequestOptions requestOptions) {
		ResponseData<Book> page = super.listEntities(requestOptions);
		page.getContent().forEach(b -> b.getAuthors().size());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Book getByIdWithAuthors(Serializable id) {
		Book book = repository.getById(id);
		book.getAuthors().size();
		return book;
	}

}
