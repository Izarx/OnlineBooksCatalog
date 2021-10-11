package com.softserveinc.booklibrary.service;

import com.softserveinc.booklibrary.entity.Author;

public interface AuthorService {

	Author create(Author author);

	Author getById(Integer id);

	Author update(Author author);

	void delete(Integer id);
}
