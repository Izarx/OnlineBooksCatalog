package com.softserveinc.booklibrary.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ReviewDtoWithoutBook {

	private Integer reviewId;
	private String commenterName;
	private String comment;
	private Integer rating;

}
