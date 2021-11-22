package com.softserveinc.booklibrary.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ReviewDto {

	private Integer reviewId;
	private String commenterName;
	private String comment;
	private Integer rating;

}
