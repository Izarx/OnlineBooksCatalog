package com.softserveinc.booklibrary.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.softserveinc.booklibrary.backend.dto.AuthorDto;
import com.softserveinc.booklibrary.backend.dto.DtoEntityConverter;
import com.softserveinc.booklibrary.backend.dto.paging.MyPage;
import com.softserveinc.booklibrary.backend.dto.paging.MyPageable;
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

	private final AuthorService authorService;

	public AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<AuthorDto> getAuthor(@PathVariable Integer id) {
		Author author = authorService.getById(id);
		return author != null ? ResponseEntity.ok(new AuthorDto(author))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ResponseEntity<MyPage<AuthorDto>> listAuthors(
			@RequestBody MyPageable pageable) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(convertPageAuthorToDto(
						authorService.listEntities(pageable)));
	}

	@PostMapping("/create")
	public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
		if (authorDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(new AuthorDto(authorService.create(authorDto.convertDtoToEntity())));
	}

	@PutMapping("/update")
	public ResponseEntity<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto) {
		if (authorDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(new AuthorDto(authorService.update(authorDto.convertDtoToEntity())));
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
				.body(convertListAuthorToDto(authorService.getAll()));
	}

	private static List<AuthorDto> convertListAuthorToDto(List<Author> authors) {
		return authors.stream().map(AuthorDto::new).collect(Collectors.toList());
	}

	private static MyPage<AuthorDto> convertPageAuthorToDto(MyPage<Author> page) {
		MyPage<AuthorDto> authorDtoPage = DtoEntityConverter.convertPageEntityDto(page);
		authorDtoPage.setContent(convertListAuthorToDto(page.getContent()));
		return authorDtoPage;
	}

}
