package com.softserveinc.booklibrary.backend.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AuthorWithBooksDto {

	private Integer authorId;
	private String firstName;
	private String lastName;
	private BigDecimal authorRating;
	private List<BookNameDto> books;

}
