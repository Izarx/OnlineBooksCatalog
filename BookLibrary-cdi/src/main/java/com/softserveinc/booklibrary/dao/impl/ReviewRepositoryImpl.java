package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.ReviewRepository;
import com.softserveinc.booklibrary.entity.Review;
import com.softserveinc.booklibrary.exception.NotValidIdValueException;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl extends AbstractEntityRepository<Review> implements ReviewRepository {
	@Override
	public Review create(Review review) {
		if (review != null) {
			Integer id = review.getReviewId();
			if (id != null) {
				throw new NotValidIdValueException(id);
			}
			entityManager.persist(review);
			return review;
		}
		return null;
	}

	@Override
	public Review update(Review review) {
		if (review != null) {
			Integer id = review.getReviewId();
			if (id == null || id <= 0) {
				throw new NotValidIdValueException(id);
			}
			entityManager.merge(review);
			return review;
		}
		return null;
	}
}
