package com.softserveinc.booklibrary.backend.repository;

import com.softserveinc.booklibrary.backend.dto.filtering.BookFilter;
import com.softserveinc.booklibrary.backend.entity.Book;

public interface BookRepository extends EntityRepository<Book, BookFilter> {

}
