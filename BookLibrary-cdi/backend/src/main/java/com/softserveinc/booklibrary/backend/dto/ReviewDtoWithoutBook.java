package com.softserveinc.booklibrary.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReviewDtoWithoutBook {

	private Integer reviewId;
	private String commenterName;
	private String comment;
	private Integer rating;

}
