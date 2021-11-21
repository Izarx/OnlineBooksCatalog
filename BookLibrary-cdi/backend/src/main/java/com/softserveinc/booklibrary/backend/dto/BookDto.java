package com.softserveinc.booklibrary.backend.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.softserveinc.booklibrary.backend.entity.Book;
import lombok.Getter;

@Getter
public final class BookDto implements MyAppDto<Book> {

	private Integer bookId;
	private String name;
	private Integer yearPublished;
	private Long isbn;
	private String publisher;
	private Double bookRating;
	private List<AuthorDto> authors;

	public BookDto() {
	}

	public BookDto(Book book) {
		bookId = book.getBookId();
		name = book.getName();
		yearPublished = book.getYearPublished();
		isbn = book.getIsbn();
		publisher = book.getPublisher();
		bookRating = book.getBookRating();
		authors = AuthorDto.convertListAuthorToDto(new ArrayList<>(book.getAuthors()));
	}

	@Override
	public Book convertDtoToEntity() {
		Book book = new Book();
		book.setBookId(bookId);
		book.setName(name);
		book.setYearPublished(yearPublished);
		book.setIsbn(isbn);
		book.setPublisher(publisher);
		return book;
	}

	public static List<BookDto> convertListBookToDto(List<Book> books) {
		return books.stream().map(BookDto::new).collect(Collectors.toList());
	}
}
