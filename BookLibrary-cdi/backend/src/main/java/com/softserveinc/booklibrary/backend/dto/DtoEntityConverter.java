package com.softserveinc.booklibrary.backend.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.entity.MyAppEntity;
import com.softserveinc.booklibrary.backend.entity.Review;

public final class DtoEntityConverter {

	private DtoEntityConverter() {
	}

	public static <T extends MyAppDto<? extends MyAppEntity<? extends Serializable>>>
				List<MyAppEntity<? extends Serializable>> convertListDtoToEntity (List<T> dtos) {
		return dtos.stream().map(MyAppDto::convertDtoToEntity).collect(Collectors.toList());
	}

	public static List<AuthorDto> convertListAuthorToDto (List<Author> authors) {
		return authors.stream().map(AuthorDto::new).collect(Collectors.toList());
	}

	public static List<BookDto> convertListBookToDto (List<Book> books) {
		return books.stream().map(BookDto::new).collect(Collectors.toList());
	}

	public static List<ReviewDto> convertListReviewToDto (List<Review> reviews) {
		return reviews.stream().map(ReviewDto::new).collect(Collectors.toList());
	}
}
