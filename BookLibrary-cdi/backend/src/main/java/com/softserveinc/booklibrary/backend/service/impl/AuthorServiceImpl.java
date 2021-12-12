package com.softserveinc.booklibrary.backend.service.impl;

import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.pagination.filtering.AuthorFilter;
import com.softserveinc.booklibrary.backend.repository.AuthorRepository;
import com.softserveinc.booklibrary.backend.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl extends AbstractEntityService<Author, AuthorFilter> implements AuthorService {

	@Autowired
	public AuthorServiceImpl(AuthorRepository authorRepository) {
		repository = authorRepository;
	}
}
