package com.softserveinc.booklibrary.backend.service;

import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.pagination.filtering.AuthorFilter;

public interface AuthorService extends EntityService<Author, AuthorFilter> {
	Author getByIdWithBooks(Integer id);
}
