package com.softserveinc.booklibrary.backend.repository;

import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.pagination.filtering.AuthorFilter;

public interface AuthorRepository extends EntityRepository<Author, AuthorFilter> {

}