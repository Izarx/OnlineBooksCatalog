package com.softserveinc.booklibrary.backend.dto.filtering;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookFilter {

	private String name;
	private AuthorFilter authorFilter;
	private BigDecimal bookRatingFrom;
	private BigDecimal bookRatingTo;
	private Integer year;
	private String isbn;

}
