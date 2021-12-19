package com.softserveinc.booklibrary.backend.service.impl;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.exception.NotValidEntityException;
import com.softserveinc.booklibrary.backend.exception.NotValidIdException;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.ResponseData;
import com.softserveinc.booklibrary.backend.pagination.filtering.AuthorFilter;
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

	@Mock
	private Author upToDateAuthor;

	@Mock
	private ResponseData<Author> authorResponseData;

	@Mock
	private RequestOptions<AuthorFilter> authorFilterRequestOptions;

	@InjectMocks
	private AuthorServiceImpl authorService;

	@Test
	void getByIdWithBooksTest_WhenAuthorIsNotNull() {
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
		verify(repository).create(author);
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

	@Test
	void updateAuthorTest_WhenAuthorIdIsNotNull() {
		doReturn(true).when(repository).isEntityValid(author);
		doReturn(ID).when(author).getEntityId();
		doReturn(author).when(repository).getById(ID);
		doReturn(upToDateAuthor).when(repository).update(author);
		var result = authorService.update(author);
		assertThat(result).isEqualTo(upToDateAuthor);
		verify(repository).update(author);
	}

	@Test
	void updateAuthorTest_WhenAuthorIsNotValid_ThenThrowsNotValidEntityException() {
		doReturn(false).when(repository).isEntityValid(author);
		assertThatThrownBy(() -> authorService.update(author))
				.isInstanceOf(NotValidEntityException.class)
				.hasMessage(String.format("Entity %s is not valid!", author));
	}

	@Test
	void updateAuthorTest_WhenAuthorIdIsNull_ThenThrowsNotValidIdException() {
		doReturn(true).when(repository).isEntityValid(author);
		doReturn(null).when(author).getEntityId();
		assertThatThrownBy(() -> authorService.update(author))
				.isInstanceOf(NotValidIdException.class)
				.hasMessage(String.format("ID object %s is not valid in this case!", null));
	}

	@Test
	void updateAuthorTest_WhenAuthorIsNull_ThenThrowsNotValidIdException() {
		doReturn(true).when(repository).isEntityValid(author);
		doReturn(ID).when(author).getEntityId();
		doReturn(null).when(repository).getById(ID);
		assertThatThrownBy(() -> authorService.update(author))
				.isInstanceOf(NotValidIdException.class)
				.hasMessage(String.format("ID object %s is not valid in this case!", ID));
	}

	@Test
	void getByIdAuthorTest_WhenAuthorIdIsNotNull() {
		doReturn(author).when(repository).getById(ID);
		var result = authorService.getById(ID);
		assertThat(result).isEqualTo(author);
		verify(repository).getById(ID);
	}

	@Test
	void getByIDAuthorTest_WhenAuthorIdIsNull_ThenThrowsNotValidIdException(){
		assertThatThrownBy(() -> authorService.getById(null))
				.isInstanceOf(NotValidIdException.class)
				.hasMessage(String.format("ID object %s is not valid in this case!", null));
	}

	@Test
	void deleteAuthor_WhenAuthorIdIsNotNull() {
		doReturn(true).when(repository).delete(ID);
		var result = authorService.delete(ID);
		assertThat(result).isTrue();
		verify(repository).delete(ID);
	}

	@Test
	void deleteAuthor_WhenAuthorIdIsNull_ThenThrowsNotValidIdException() {
		var result = authorService.delete(null);
		assertThat(result).isFalse();
	}

	@Test
	void getAllAuthors() {
		List<Author> authorList = List.of(author);
		doReturn(authorList).when(repository).getAll();
		var result = authorService.getAll();
		assertThat(result).hasSize(1).containsExactly(author);
		verify(repository).getAll();
	}

	@Test
	void listEntitiesAuthors() {
		doReturn(authorResponseData).when(repository).listEntities(authorFilterRequestOptions);
		var result = authorService.listEntities(authorFilterRequestOptions);
		assertThat(result).isEqualTo(authorResponseData);
		verify(repository).listEntities(authorFilterRequestOptions);
	}

	@Test
	void bulkDeleteAuthors() {
		List<Serializable> listIds = List.of(ID);
		List<Author> unavailableToDeleteEntities = List.of(author);
		doReturn(unavailableToDeleteEntities).when(repository).bulkDeleteEntities(listIds);
		var result = authorService.bulkDeleteEntities(listIds);
		assertThat(result).hasSize(1).containsExactly(author);
		verify(repository).bulkDeleteEntities(listIds);
	}


}