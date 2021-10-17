package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.ReviewRepository;
import com.softserveinc.booklibrary.entity.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl extends AbstractEntityRepository<Review, Integer> implements ReviewRepository {
	@Override
	protected Integer getId(Review review) {
		return review.getReviewId();
	}
}
