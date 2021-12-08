package com.softserveinc.booklibrary.backend.dto.filtering;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookFilter {

	private String name;
	private AuthorFilter authorFilter;  // todo: why are you object here?
	private BigDecimal bookRatingFrom;  // todo rename to ratingFrom
	private BigDecimal bookRatingTo;    // todo rename to ratingTo
	private Integer year;
	private String isbn;

}
