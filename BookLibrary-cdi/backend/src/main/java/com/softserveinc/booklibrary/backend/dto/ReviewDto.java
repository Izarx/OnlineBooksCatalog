package com.softserveinc.booklibrary.backend.dto;

import com.softserveinc.booklibrary.backend.entity.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public final class ReviewDto {

	private Integer reviewId;
	private String commenterName;
	private String comment;
	private Integer rating;
	private Book book;

}
