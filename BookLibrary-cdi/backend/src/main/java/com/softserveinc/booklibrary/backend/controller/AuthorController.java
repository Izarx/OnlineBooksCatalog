package com.softserveinc.booklibrary.backend.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.softserveinc.booklibrary.backend.dto.ApplicationMapper;
import com.softserveinc.booklibrary.backend.dto.AuthorDto;
import com.softserveinc.booklibrary.backend.dto.AuthorNameDto;
import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.ResponseData;
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
		Author author = authorService.getById(id);
		return author != null ? ResponseEntity.ok(appMapper.authorToAuthorDto(author))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
			@RequestBody RequestOptions requestOptions) {
		return ResponseEntity
				.ok(convertAuthorPageToAuthorDtoPage(
						authorService.listEntities(requestOptions)));
	}

	@PostMapping("/create")
	public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
		if (authorDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
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
				ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<AuthorDto>> getAllAuthors() {
		return ResponseEntity
				.ok(appMapper.listAuthorsToListAuthorsDto(authorService.getAll()));
	}

	private ResponseData<AuthorDto> convertAuthorPageToAuthorDtoPage(
			ResponseData<Author> responseData) {
		ResponseData<AuthorDto> entityDtoPage = new ResponseData<>();
		entityDtoPage.setTotalElements(responseData.getTotalElements());
		entityDtoPage.setContent(appMapper.listAuthorsToListAuthorsDto(responseData.getContent()));
		return entityDtoPage;
	}

}
