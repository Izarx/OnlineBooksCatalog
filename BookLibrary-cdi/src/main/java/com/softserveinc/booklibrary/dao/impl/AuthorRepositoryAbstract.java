package com.softserveinc.booklibrary.dao.impl;

import javax.transaction.Transactional;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryAbstract extends AbstractEntityRepository<Author> implements AuthorRepository {

	@Override
	@Transactional
	public Author getById(Integer id) {
		Author author = entityManager.find(Author.class, id);
		return author;
	}
}
