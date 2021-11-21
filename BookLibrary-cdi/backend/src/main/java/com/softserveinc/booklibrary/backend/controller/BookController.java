package com.softserveinc.booklibrary.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.softserveinc.booklibrary.backend.dto.BookDto;
import com.softserveinc.booklibrary.backend.dto.DtoEntityConverter;
import com.softserveinc.booklibrary.backend.dto.paging.MyPage;
import com.softserveinc.booklibrary.backend.dto.paging.MyPageable;
import com.softserveinc.booklibrary.backend.dto.paging.PageConstructor;
import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/api/books")
public class BookController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDto> getBook(@PathVariable Integer id) {
		Book book = bookService.getById(id);
		return book != null ? ResponseEntity.ok(new BookDto(book))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ResponseEntity<MyPage<BookDto>> listBooks(@RequestBody PageConstructor pageConstructor) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(convertPageBookToDto(
						bookService.listEntities(pageConstructor)));
	}

	@PostMapping("/create")
	public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
		if (bookDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(new BookDto(bookService.create(bookDto.convertDtoToEntity())));
	}

	@PatchMapping("/update")
	public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
		if (bookDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(new BookDto(bookService.update(bookDto.convertDtoToEntity())));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<BookDto> deleteBook(@PathVariable Integer id) {
		return bookService.delete(id) ?
				ResponseEntity.ok().build() :
				ResponseEntity.notFound().build();
	}

	private static List<BookDto> convertListBookToDto(List<Book> books) {
		return books.stream().map(BookDto::new).collect(Collectors.toList());
	}

	private static MyPage<BookDto> convertPageBookToDto(MyPage<Book> page) {
		MyPage<BookDto> bookDtoPage = DtoEntityConverter.convertPageEntityDto(page);
		bookDtoPage.setContent(convertListBookToDto(page.getContent()));
		return bookDtoPage;
	}

}
