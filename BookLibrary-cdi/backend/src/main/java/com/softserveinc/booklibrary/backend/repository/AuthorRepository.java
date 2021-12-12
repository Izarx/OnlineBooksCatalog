package com.softserveinc.booklibrary.backend.repository;

import com.softserveinc.booklibrary.backend.pagination.filtering.AuthorFilter;
import com.softserveinc.booklibrary.backend.entity.Author;

public interface AuthorRepository extends EntityRepository<Author, AuthorFilter> {

}