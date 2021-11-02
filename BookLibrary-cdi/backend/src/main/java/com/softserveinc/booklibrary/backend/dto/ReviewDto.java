package com.softserveinc.booklibrary.backend.dto;

import com.softserveinc.booklibrary.backend.entity.Review;
import lombok.Getter;

@Getter
public final class ReviewDto implements MyAppDto<Review> {

	private Integer reviewId;
	private String commenterName;
	private String comment;
	private Integer rating;

	public ReviewDto() {}

	public ReviewDto(Review review) {
		reviewId = review.getReviewId();
		commenterName = review.getCommenterName();
		comment = review.getComment();
		rating = review.getRating();
	}

	@Override
	public Review convertDtoToEntity() {
		Review review = new Review();
		review.setReviewId(reviewId);
		review.setCommenterName(commenterName);
		review.setComment(comment);
		review.setRating(rating);
		return review;
	}
}
