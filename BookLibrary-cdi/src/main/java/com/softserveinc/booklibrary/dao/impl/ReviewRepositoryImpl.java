package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.ReviewRepository;
import com.softserveinc.booklibrary.entity.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl extends AbstractEntityRepository<Review> implements ReviewRepository {
	@Override
	public Review create(Review review) {
		if (review != null && review.getReviewId() == null) {
			entityManager.persist(review);
			return review;
		}
		return null;
	}

	@Override
	public Review update(Review review) {
		if (review != null && review.getReviewId() != null) {
			entityManager.merge(review);
			return review;
		}
		return null;
	}
}
