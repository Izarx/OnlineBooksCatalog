package com.softserveinc.booklibrary.backend.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public final class BookDto {

	private Integer bookId;
	private String name;
	private Integer yearPublished;
	private String isbn;
	private String publisher;
	private BigDecimal bookRating;
	private List<AuthorDto> authors;

}
