package com.softserveinc.booklibrary.backend.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorDto {

	private Integer authorId;
	private String firstName;
	private String lastName;
	private BigDecimal authorRating;

}
