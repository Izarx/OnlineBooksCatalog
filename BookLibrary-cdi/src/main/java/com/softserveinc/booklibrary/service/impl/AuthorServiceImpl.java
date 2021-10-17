package com.softserveinc.booklibrary.service.impl;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import com.softserveinc.booklibrary.exception.NullEntityException;
import com.softserveinc.booklibrary.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl extends AbstractEntityService<Author> implements AuthorService {

	@Autowired
	public AuthorServiceImpl(AuthorRepository authorRepository) {
		repository = authorRepository;
	}

	@Override
	public Author create(Author entity) {
		if (entity == null) {
			throw new NullEntityException();
		}
		return null;
	}

	@Override
	public Author update(Author entity) {
		return null;
	}
}
