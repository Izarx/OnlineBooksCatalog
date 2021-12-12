package com.softserveinc.booklibrary.backend.pagination.filtering;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookFilter {

	private String name;
	private String authorName;  // todo: why are you object here?
	private BigDecimal ratingFrom;  // todo rename to ratingFrom
	private BigDecimal ratingTo;    // todo rename to ratingTo
	private Integer year;
	private String isbn;

}
