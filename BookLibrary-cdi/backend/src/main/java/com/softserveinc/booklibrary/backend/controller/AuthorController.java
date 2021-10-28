package com.softserveinc.booklibrary.backend.controller;

import java.util.List;

import com.softserveinc.booklibrary.backend.dto.AuthorDto;
import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

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

	@PostMapping("/create")
	public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
		if (authorDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(new AuthorDto(authorService.create(authorDto.convertDtoToEntity())));
	}

	@PatchMapping("/update")
	public ResponseEntity<AuthorDto> updateAuthor(@RequestBody AuthorDto authorDto) {
		if (authorDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(new AuthorDto(authorService.create(authorDto.convertDtoToEntity())));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<AuthorDto> deleteAuthor(@PathVariable Integer id) {
		return authorService.delete(id) ?
				ResponseEntity.ok().build() :
				ResponseEntity.notFound().build();
	}

	@GetMapping
	public ResponseEntity<List<AuthorDto>> getAllAuthors() {
		return null;
	}

}
