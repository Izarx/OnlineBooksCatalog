package com.softserveinc.booklibrary.backend.service.impl;

import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.pagination.filtering.AuthorFilter;
import com.softserveinc.booklibrary.backend.repository.AuthorRepository;
import com.softserveinc.booklibrary.backend.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorServiceImpl extends AbstractEntityService<Author, AuthorFilter> implements AuthorService {

	@Autowired
	public AuthorServiceImpl(AuthorRepository authorRepository) {
		repository = authorRepository;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Author getByIdWithBooks(Integer id) {
		Author author = repository.getById(id);
		if (author != null) {
			author.getBooks().size();
		}
		return author;
	}
}
