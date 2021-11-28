package com.softserveinc.booklibrary.backend.dto;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class BookDto {

	private Integer bookId;
	private String name;
	private Integer yearPublished;
	private Long isbn;
	private String publisher;
	private BigDecimal bookRating;
	private Set<AuthorDto> authors;

}
