package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.AuthorRepository;
import com.softserveinc.booklibrary.entity.Author;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryImpl extends AbstractEntityRepository<Author, Integer> implements AuthorRepository {

	@Override
	public boolean isEntityValid(Author author) {
		String firstName = author.getFirstName();
		String lastName = author.getLastName();
		if (firstName == null || firstName.length() > Author.FIRST_NAME_LENGTH) {
			return false;
		}
		if (lastName == null || lastName.length() > Author.LAST_NAME_LENGTH) {
			return false;
		}
		return true;
	}
}
