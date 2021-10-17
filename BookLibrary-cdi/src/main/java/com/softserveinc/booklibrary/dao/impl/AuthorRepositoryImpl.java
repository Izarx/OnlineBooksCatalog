package com.softserveinc.booklibrary.dao.impl;

import javax.persistence.EntityNotFoundException;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import com.softserveinc.booklibrary.exception.NotValidIdValueException;
import com.softserveinc.booklibrary.exception.NullEntityException;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryImpl extends AbstractEntityRepository<Author> implements AuthorRepository {

	@Override
	public Author create(Author author) {
		if (author == null) {
			throw new NullEntityException();
		}
		Integer id = author.getAuthorId();
		if (id != null) {
			throw new NotValidIdValueException(id);
		}
		entityManager.persist(author);
		return author;
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
		if (entityManager.find(Author.class, id) == null) {
			throw new EntityNotFoundException(author.getClass() + " with ID = " + id + " not found!");
		}
		return entityManager.merge(author);
	}
}
