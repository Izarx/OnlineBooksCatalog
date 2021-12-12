package com.softserveinc.booklibrary.backend.pagination.filtering;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorFilter {

	private String name;
	private BigDecimal ratingFrom;    // todo rename to ratingFrom
	private BigDecimal ratingTo;      // todo rename to ratingTo

}
