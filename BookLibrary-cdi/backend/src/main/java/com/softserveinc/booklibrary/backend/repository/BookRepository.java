package com.softserveinc.booklibrary.backend.repository;

import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.pagination.filtering.BookFilter;

public interface BookRepository extends EntityRepository<Book, BookFilter> {

}
