package com.softserveinc.booklibrary.backend.service.impl;

import java.io.Serializable;

import com.softserveinc.booklibrary.backend.dto.filtering.BookFilter;
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
public class BookServiceImpl extends AbstractEntityService<Book, BookFilter> implements BookService {

	@Autowired
	public BookServiceImpl(BookRepository bookRepository) {
		repository = bookRepository;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ResponseData<Book> listEntities(RequestOptions<BookFilter> requestOptions) {
		ResponseData<Book> page = super.listEntities(requestOptions);   // todo: rename variable
		// todo: need to add sorting authors by name
		page.getContent().forEach(b -> b.getAuthors().size());
		return page;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Book getByIdWithAuthors(Serializable id) {   // todo: why Serializable ?
		Book book = repository.getById(id);
		book.getAuthors().size();
		return book;
	}

}
