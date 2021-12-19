package com.softserveinc.booklibrary.backend.service.impl;

import java.util.HashSet;

import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

	private static final Integer ID = 1;

	@Mock
	private BookRepository repository;

	@Mock
	private Book book;

	@InjectMocks
	private BookServiceImpl bookService;

	@Test
	void getByIdWithAuthorsTest_WhenAuthorIsNotNull() {
		doReturn(book).when(repository).getById(ID);
		doReturn(new HashSet<Book>()).when(book).getAuthors();
		var result = bookService.getByIdWithAuthors(ID);
		assertThat(result).isEqualTo(book);
		verify(repository).getById(ID);
		verify(book).getAuthors();
	}

	@Test
	void getByIdWithAuthorsTest_WhenAuthorIsNull() {
		doReturn(null).when(repository).getById(ID);
		var result = bookService.getByIdWithAuthors(ID);
		assertThat(result).isNull();
		verify(repository).getById(ID);
	}
}