package com.softserveinc.booklibrary.backend.dto;

import com.softserveinc.booklibrary.backend.entity.Book;
import lombok.Getter;

@Getter
public final class BookDto implements MyAppDto<Book> {

	private Integer bookId;
	private String name;
	private Integer yearPublished;
	private Long isbn;
	private String publisher;

	public BookDto() {
	}

	public BookDto(Book book) {
		bookId = book.getBookId();
		name = book.getName();
		yearPublished = book.getYearPublished();
		isbn = book.getIsbn();
		publisher = book.getPublisher();
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
}
