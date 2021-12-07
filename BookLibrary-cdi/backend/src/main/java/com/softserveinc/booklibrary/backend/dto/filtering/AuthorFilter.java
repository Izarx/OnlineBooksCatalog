package com.softserveinc.booklibrary.backend.dto.filtering;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorFilter {

	private String name;
	private BigDecimal authorRatingFrom;
	private BigDecimal authorRatingTo;

}
