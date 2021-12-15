package com.softserveinc.booklibrary.backend.controller;

import java.util.ArrayList;
import java.util.List;

import com.softserveinc.booklibrary.backend.dto.ApplicationMapper;
import com.softserveinc.booklibrary.backend.dto.AuthorDto;
import com.softserveinc.booklibrary.backend.dto.AuthorNameDto;
import com.softserveinc.booklibrary.backend.dto.AuthorWithBooksDto;
import com.softserveinc.booklibrary.backend.dto.BookDto;
import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.ResponseData;
import com.softserveinc.booklibrary.backend.pagination.filtering.AuthorFilter;
import com.softserveinc.booklibrary.backend.service.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorController.class);

	private final ApplicationMapper appMapper;

	private final AuthorService authorService;

	public AuthorController(ApplicationMapper appMapper, AuthorService authorService) {
		this.appMapper = appMapper;
		this.authorService = authorService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthorDto> getAuthor(@PathVariable Integer id) {
		LOGGER.info("Getting Author, AuthorController, the path variable is ID = {}", id);
		Author author = authorService.getById(id);
		if (author == null) {
			LOGGER.warn("Getting Author, AuthorController, Author with ID = {} not found", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			LOGGER.debug("Getting Author, AuthorController, Author with ID = {} is {}", id, author);
		}
		return ResponseEntity.ok(appMapper.authorToAuthorDto(author));
	}

	@PostMapping("/bulk-delete")
	public ResponseEntity<List<AuthorNameDto>> bulkDeleteAuthors(
			@RequestBody List<Integer> authorsIdsForDelete) {
		LOGGER.info("Bulk Delete Authors, AuthorController, deleting authors with IDs = {}", authorsIdsForDelete);
		return ResponseEntity
				.ok(appMapper.listAuthorsToListAuthorsNameDto(
						authorService.bulkDeleteEntities(new ArrayList<>(authorsIdsForDelete))));
	}

	@PostMapping
	public ResponseEntity<ResponseData<AuthorDto>> listAuthors(
			@RequestBody RequestOptions<AuthorFilter> requestOptions) {
		LOGGER.debug("Getting Filtered Sorted Page of Authors, AuthorController, Request options to fetch page of Authors is {}", requestOptions);
		return ResponseEntity
				.ok(convertAuthorResponseToAuthorDtoResponse(
						authorService.listEntities(requestOptions)));
	}

	@PostMapping("/create")
	public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
		if (authorDto == null) {
			LOGGER.warn("Creating Author, AuthorController, Transfer object which received from UI is empty!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		LOGGER.debug("Creating Author, AuthorController, Transfer object which received from UI is {}", authorDto);
		return ResponseEntity
				.ok(appMapper.authorToAuthorDto(authorService
						.create(appMapper
								.authorDtoToAuthor(authorDto))));
	}

	@PutMapping("/update")
	public ResponseEntity<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto) {
		if (authorDto == null) {
			LOGGER.warn("Updating Author, AuthorController, Transfer object which received from UI with updating author information is empty!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		LOGGER.info("Updating Author, AuthorController, Transfer object which received from UI with updating author information is {}", authorDto);
		return ResponseEntity
				.ok(appMapper.authorToAuthorDto(authorService
						.update(appMapper
								.authorDtoToAuthor(authorDto))));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable Integer id) {
		LOGGER.info("Deleting Author, AuthorController, the path variable is ID = {}.", id);
		return authorService.delete(id) ?
				ResponseEntity.ok().build() :
				ResponseEntity.noContent().build(); // todo: reason?
	}

	@GetMapping
	public ResponseEntity<List<AuthorDto>> getAllAuthors() {
		return ResponseEntity
				.ok(appMapper.listAuthorsToListAuthorsDto(authorService.getAll()));
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<AuthorWithBooksDto> getAuthorsWithBooks(@PathVariable Integer id) {
		LOGGER.info("Getting Author, AuthorController, the path variable is ID = {}", id);
		Author author = authorService.getByIdWithBooks(id);
		if (author == null) {
			LOGGER.warn("Getting Author, AuthorController, Author with ID = {} not found", id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			LOGGER.debug("Getting Author, AuthorController, Author with ID = {} is {}", id, author);
		}
		return ResponseEntity.ok(appMapper.authorToAuthorWithBooksDto(author));
	}

	private ResponseData<AuthorDto> convertAuthorResponseToAuthorDtoResponse(
			ResponseData<Author> responseData) {
		ResponseData<AuthorDto> authorDtoResponseData = new ResponseData<>();
		if (responseData != null) {
			LOGGER.debug("Converting Response Data with Authors to Response Data with Authors DTOs, AuthorController, " +
					"response data BEFORE converting: total number of authors = {} ; list with authors = {}",
					responseData.getTotalElements(), responseData.getContent());
			authorDtoResponseData.setTotalElements(responseData.getTotalElements());
			authorDtoResponseData.setContent(appMapper.listAuthorsToListAuthorsDto(responseData.getContent()));
		}
		else {
			LOGGER.warn("Converting Response Data with Authors to Response Data with Authors DTOs, AuthorController, " +
					"response data is empty!");
		}
		LOGGER.debug("Converting Response Data with Authors to Response Data with Authors DTOs, AuthorController, " +
						"response data AFTER converting: total number of authors = {} ; size list of authors = {}",
				authorDtoResponseData.getTotalElements(), authorDtoResponseData.getContent().size());
		return authorDtoResponseData;
	}

}
