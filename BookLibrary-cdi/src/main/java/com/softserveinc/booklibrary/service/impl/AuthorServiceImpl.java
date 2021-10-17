package com.softserveinc.booklibrary.service.impl;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import com.softserveinc.booklibrary.exception.NotValidIdValueException;
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
	public Author create(Author author) {
		if (author == null) {
			throw new NullEntityException();
		}
		Integer id = author.getAuthorId();
		if (id != null) {
			throw new NotValidIdValueException(id);
		}
		return repository.create(author);
	}

	@Override
	public Author update(Author author) {
		if (author == null) {
			throw new NullEntityException();
		}
		Integer id = author.getAuthorId();
		if (id == null || id <= 0) {
			throw new NotValidIdValueException(id);
		}
		return repository.update(author);
	}
}
