package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import com.softserveinc.booklibrary.exception.NotValidIdValueException;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryImpl extends AbstractEntityRepository<Author> implements AuthorRepository {

	@Override
	public Author create(Author author) {
		if (author != null) {
			Integer id = author.getAuthorId();
			if (id != null) {
				throw new NotValidIdValueException(id);
			}
			entityManager.persist(author);
			return author;
		}
		return null;
	}

	@Override
	public Author update(Author author) {
		if (author != null) {
			Integer id = author.getAuthorId();
			if (id == null || id <= 0) {
				throw new NotValidIdValueException(id);
			}
			entityManager.merge(author);
			return author;
		}
		return null;
	}
}
