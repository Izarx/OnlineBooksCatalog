package com.softserveinc.booklibrary.backend.controller;

import java.util.ArrayList;
import java.util.List;

import com.softserveinc.booklibrary.backend.dto.ApplicationMapper;
import com.softserveinc.booklibrary.backend.dto.AuthorDto;
import com.softserveinc.booklibrary.backend.dto.AuthorNameDto;
import com.softserveinc.booklibrary.backend.dto.filtering.AuthorFilter;
import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.ResponseData;
import com.softserveinc.booklibrary.backend.service.AuthorService;
import org.apache.commons.lang3.ObjectUtils;
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
		LOGGER.info(String.format("You're looking for Author with ID = %d", id));
		Author author = authorService.getById(id);
		if (author == null) {
			LOGGER.warn(String.format("Author with ID = %d not found", id));
			ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		else {
			LOGGER.info(String.format("Author with ID = %d is %s", id, author));
		}
		return ResponseEntity.ok(appMapper.authorToAuthorDto(author));
	}

	@PostMapping("/bulk-delete")
	public ResponseEntity<List<AuthorNameDto>> bulkDeleteAuthors(
			@RequestBody List<Integer> authorsIdsForDelete) {
		return ResponseEntity
				.ok(appMapper.listAuthorsToListAuthorsNameDto(
						authorService.bulkDeleteEntities(new ArrayList<>(authorsIdsForDelete))));
	}

	@PostMapping
	public ResponseEntity<ResponseData<AuthorDto>> listAuthors(
			@RequestBody RequestOptions<AuthorFilter> requestOptions) {
		return ResponseEntity
				.ok(convertAuthorResponseToAuthorDtoResponse(
						authorService.listEntities(requestOptions)));
	}

	@PostMapping("/create")
	public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
		if (authorDto == null) {
			LOGGER.warn("Transfer object which received from UI with creating author information is empty!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		LOGGER.info("Transfer object which received from UI with creating author information is {}", authorDto);
		return ResponseEntity
				.ok(appMapper.authorToAuthorDto(authorService
						.create(appMapper
								.authorDtoToAuthor(authorDto))));
	}

	@PutMapping("/update")
	public ResponseEntity<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto) {
		if (authorDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity
				.ok(appMapper.authorToAuthorDto(authorService
						.update(appMapper
								.authorDtoToAuthor(authorDto))));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable Integer id) {

		return authorService.delete(id) ?
				ResponseEntity.ok().build() :
				ResponseEntity.noContent().build(); // todo: reason?
	}

	@GetMapping
	public ResponseEntity<List<AuthorNameDto>> getAllAuthors() {
		return ResponseEntity
				.ok(appMapper.listAuthorsToListAuthorsNameDto(authorService.getAll()));
	}

	private ResponseData<AuthorDto> convertAuthorResponseToAuthorDtoResponse(
			ResponseData<Author> responseData) {
		ResponseData<AuthorDto> authorDtoResponseData = new ResponseData<>();
		if (ObjectUtils.isNotEmpty(responseData)){
			authorDtoResponseData.setTotalElements(responseData.getTotalElements());
			authorDtoResponseData.setContent(appMapper.listAuthorsToListAuthorsDto(responseData.getContent()));
		}
		return authorDtoResponseData;
	}

}
