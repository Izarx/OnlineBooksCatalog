package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryImpl extends AbstractEntityRepository<Author> implements AuthorRepository {

	@Override
	public Author getById(Integer id) {
		return entityManager.find(Author.class, id);
	}

}
