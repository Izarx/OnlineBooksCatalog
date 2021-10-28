package com.softserveinc.booklibrary.backend.dto;

import com.softserveinc.booklibrary.backend.entity.Book;
import lombok.Getter;

@Getter
public final class BookDto implements MyAppDto<Book> {

	private final Integer bookId;
	private final String name;
	private final Integer yearPublished;
	private final Long isbn;
	private final String publisher;


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
