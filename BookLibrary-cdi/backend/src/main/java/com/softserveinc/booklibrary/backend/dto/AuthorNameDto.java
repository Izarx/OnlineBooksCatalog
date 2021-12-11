package com.softserveinc.booklibrary.backend.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthorNameDto {

	private Integer authorId;
	private String firstName;
	private String lastName;

}
