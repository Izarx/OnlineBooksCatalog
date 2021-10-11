package com.softserveinc.booklibrary.service.impl;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import com.softserveinc.booklibrary.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

	private final Logger logger = LoggerFactory.getLogger(AuthorServiceImpl.class);

	private final AuthorRepository authorRepository;

	@Autowired
	public AuthorServiceImpl(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	@Override
	public Author create(Author author) {
		return authorRepository.save(author).orElseThrow();
	}

	@Override
	public Author getById(Integer id) {
		logger.info("******************Service method getById****************");
		return authorRepository.getById(id).orElseThrow();
	}

	@Override
	public Author update(Author author) {
		authorRepository.getById(author.getAuthorId())
				.ifPresent(authorRepository::save);
		return author;
	}

	@Override
	public void delete(Integer id) {
		authorRepository.delete(getById(id));
	}
}
