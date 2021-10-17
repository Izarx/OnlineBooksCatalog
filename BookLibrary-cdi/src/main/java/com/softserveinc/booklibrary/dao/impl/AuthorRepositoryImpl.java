package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryImpl extends AbstractEntityRepository<Author, Integer> implements AuthorRepository {

	@Override
	public Integer getEntityId(Author author) {
		return author.getAuthorId();
	}
}
