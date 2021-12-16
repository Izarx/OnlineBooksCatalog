package com.softserveinc.booklibrary.backend.service.impl;

import java.util.HashSet;

import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.exception.NotValidEntityException;
import com.softserveinc.booklibrary.backend.exception.NotValidIdException;
import com.softserveinc.booklibrary.backend.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

	private static final Integer ID = 1;

	@Mock
	private AuthorRepository repository;

	@Mock
	private Author author;

	@Mock
	private Author persistedAuthor;

	@InjectMocks
	private AuthorServiceImpl authorService;

	@Test
	void getByIdWithBooksTest_WhenAuthorNotNull() {
		doReturn(author).when(repository).getById(ID);
		doReturn(new HashSet<Book>()).when(author).getBooks();
		var result = authorService.getByIdWithBooks(ID);
		assertThat(result).isEqualTo(author);
		verify(repository).getById(ID);
		verify(author).getBooks();
	}

	@Test
	void getByIdWithBooksTest_WhenAuthorIsNull() {
		doReturn(null).when(repository).getById(ID);
		var result = authorService.getByIdWithBooks(ID);
		assertThat(result).isNull();
		verify(repository).getById(ID);
	}

	@Test
	void createAuthorTest_WhenAuthorIdIsNull() {
		doReturn(true).when(repository).isEntityValid(author);
		doReturn(null).when(author).getEntityId();
		doReturn(persistedAuthor).when(repository).create(author);
		var result = authorService.create(author);
		assertThat(result).isEqualTo(persistedAuthor);
	}

	@Test
	void createAuthorTest_WhenAuthorIsNotValid_ThenThrowsNotValidEntityException() {
		doReturn(false).when(repository).isEntityValid(author);
		assertThatThrownBy(() -> authorService.create(author))
				.isInstanceOf(NotValidEntityException.class)
				.hasMessage(String.format("Entity %s is not valid!", author));
	}

	@Test
	void createAuthorTest_WhenAuthorIdIsNotNull_ThenThrowsNotValidIdException() {
		doReturn(true).when(repository).isEntityValid(author);
		doReturn(ID).when(author).getEntityId();
		assertThatThrownBy(() -> authorService.create(author))
				.isInstanceOf(NotValidIdException.class)
				.hasMessage(String.format("ID object %s is not valid in this case!", ID));
	}

}