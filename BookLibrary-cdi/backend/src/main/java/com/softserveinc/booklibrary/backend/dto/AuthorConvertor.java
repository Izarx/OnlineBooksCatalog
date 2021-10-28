package com.softserveinc.booklibrary.backend.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.softserveinc.booklibrary.backend.entity.Author;

public class AuthorConvertor {

	public static List<Author> convertDtoListToAuthorList(List<AuthorDto> dtos) {
		return dtos.stream().map(AuthorDto::convertDtoToEntity).collect(Collectors.toList());
	}

	public static List<AuthorDto> convertAuthorListToDtoList(List<Author> authors) {
		return authors.stream().map(AuthorDto::new).collect(Collectors.toList());
	}
}
