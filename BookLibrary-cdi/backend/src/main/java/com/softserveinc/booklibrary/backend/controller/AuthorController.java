package com.softserveinc.booklibrary.backend.controller;

import java.util.List;

import com.softserveinc.booklibrary.backend.dto.AuthorDto;
import com.softserveinc.booklibrary.backend.dto.CommonAppMapper;
import com.softserveinc.booklibrary.backend.dto.paging.MyPage;
import com.softserveinc.booklibrary.backend.dto.paging.PageConstructor;
import com.softserveinc.booklibrary.backend.entity.Author;
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

	private final CommonAppMapper appMapper;

	private final AuthorService authorService;

	public AuthorController(CommonAppMapper appMapper, AuthorService authorService) {
		this.appMapper = appMapper;
		this.authorService = authorService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthorDto> getAuthor(@PathVariable Integer id) {
		Author author = authorService.getById(id);
		return author != null ? ResponseEntity.ok(appMapper.authorToAuthorDto(author))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ResponseEntity<MyPage<AuthorDto>> listAuthors(
			@RequestBody PageConstructor pageConstructor) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(convertPageAuthorDto(
						authorService.listEntities(pageConstructor)));
	}

	@PostMapping("/create")
	public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
		if (authorDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(appMapper
				.authorToAuthorDto(authorService
						.create(appMapper
								.authorDtoToAuthor(authorDto))));
	}

	@PutMapping("/update")
	public ResponseEntity<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto) {
		if (authorDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(appMapper
				.authorToAuthorDto(authorService
						.update(appMapper
								.authorDtoToAuthor(authorDto))));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable Integer id) {
		return authorService.delete(id) ?
				ResponseEntity.ok().build() :
				ResponseEntity.notFound().build();
	}

	@GetMapping
	public ResponseEntity<List<AuthorDto>> getAllAuthors() {
		return ResponseEntity.status(HttpStatus.OK)
				.body(appMapper.listAuthorsToListAuthorsDto(authorService.getAll()));
	}

	public MyPage<AuthorDto> convertPageAuthorDto(MyPage<Author> page) {
		MyPage<AuthorDto> entityDtoPage = new MyPage<>();
		entityDtoPage.setPageConstructor(page.getPageConstructor());
		entityDtoPage.setLast(page.getLast());
		entityDtoPage.setTotalPages(page.getTotalPages());
		entityDtoPage.setTotalElements(page.getTotalElements());
		entityDtoPage.setFirst(page.getFirst());
		entityDtoPage.setNumberOfFirstPageElement(page.getNumberOfFirstPageElement());
		entityDtoPage.setNumberOfElements(page.getNumberOfElements());
		entityDtoPage.setContent(appMapper.listAuthorsToListAuthorsDto(page.getContent()));
		return entityDtoPage;
	}

}
