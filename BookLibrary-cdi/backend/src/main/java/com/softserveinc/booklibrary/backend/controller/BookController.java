package com.softserveinc.booklibrary.backend.controller;

import com.softserveinc.booklibrary.backend.dto.BookDto;
import com.softserveinc.booklibrary.backend.dto.CommonAppMapper;
import com.softserveinc.booklibrary.backend.dto.paging.ApplicationResponsePage;
import com.softserveinc.booklibrary.backend.dto.paging.ApplicationRequestPage;
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

	private final CommonAppMapper appMapper;

	private final BookService bookService;

	public BookController(CommonAppMapper appMapper, BookService bookService) {
		this.appMapper = appMapper;
		this.bookService = bookService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookDto> getBook(@PathVariable Integer id) {
		Book book = bookService.getById(id);
		return book != null ? ResponseEntity.ok(appMapper.bookToBookDto(book))
				: ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ResponseEntity<ApplicationResponsePage<BookDto>> listBooks(@RequestBody ApplicationRequestPage applicationRequestPage) {
		return ResponseEntity.status(HttpStatus.OK)
				.body(convertBookPageToBookDtoPage(
						bookService.listEntities(applicationRequestPage)));
	}

	@PostMapping("/create")
	public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
		if (bookDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(appMapper
				.bookToBookDto(bookService
						.create(appMapper.bookDtoToBook(bookDto))));
	}

	@PatchMapping("/update")
	public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
		if (bookDto == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.ok(appMapper
				.bookToBookDto(bookService
						.update(appMapper.bookDtoToBook(bookDto))));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<BookDto> deleteBook(@PathVariable Integer id) {
		return bookService.delete(id) ?
				ResponseEntity.ok().build() :
				ResponseEntity.notFound().build();
	}

	private ApplicationResponsePage<BookDto> convertBookPageToBookDtoPage(
			ApplicationResponsePage<Book> responsePage) {
		ApplicationResponsePage<BookDto> entityDtoPage = new ApplicationResponsePage<>();
		entityDtoPage.setTotalElements(responsePage.getTotalElements());
		entityDtoPage.setContent(appMapper.listBooksToListBooksDto(responsePage.getContent()));
		return entityDtoPage;
	}

}
