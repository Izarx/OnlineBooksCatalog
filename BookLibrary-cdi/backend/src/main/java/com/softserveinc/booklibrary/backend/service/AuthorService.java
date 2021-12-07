package com.softserveinc.booklibrary.backend.service;

import com.softserveinc.booklibrary.backend.dto.filtering.AuthorFilter;
import com.softserveinc.booklibrary.backend.entity.Author;

public interface AuthorService extends EntityService<Author, AuthorFilter> {
}
