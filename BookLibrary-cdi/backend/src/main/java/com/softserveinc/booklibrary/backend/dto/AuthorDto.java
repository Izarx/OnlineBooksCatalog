package com.softserveinc.booklibrary.backend.dto;

import com.softserveinc.booklibrary.backend.entity.Author;
import lombok.Getter;

@Getter
public final class AuthorDto implements EntityDto<Author> {

	private final Integer authorId;
	private final String firstName;
	private final String lastName;

	public AuthorDto(Author author) {
		authorId = author.getAuthorId();
		firstName = author.getFirstName();
		lastName = author.getLastName();
	}

	@Override
	public Author convertDtoToEntity() {
		Author author = new Author();
		author.setAuthorId(authorId);
		author.setFirstName(firstName);
		author.setLastName(lastName);
		return author;
	}
}
