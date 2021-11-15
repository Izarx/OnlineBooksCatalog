package com.softserveinc.booklibrary.backend.dto;

import com.softserveinc.booklibrary.backend.entity.Author;
import lombok.Getter;

@Getter
public final class AuthorDto implements MyAppDto<Author> {

	private Integer authorId;
	private String firstName;
	private String lastName;
	private Double authorRating;

	public AuthorDto() {
	}

	public AuthorDto(Author author) {
		authorId = author.getAuthorId();
		firstName = author.getFirstName();
		lastName = author.getLastName();
		authorRating = author.getAuthorRating();
	}

	@Override
	public Author convertDtoToEntity() {
		Author author = new Author();
		author.setAuthorId(authorId);
		author.setFirstName(firstName);
		author.setLastName(lastName);
		author.setAuthorRating(authorRating);
		return author;
	}
}
