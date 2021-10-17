package com.softserveinc.booklibrary.service.impl;

import com.softserveinc.booklibrary.dao.ReviewRepository;
import com.softserveinc.booklibrary.entity.Review;
import com.softserveinc.booklibrary.exception.NotValidIdValueException;
import com.softserveinc.booklibrary.exception.NullEntityException;
import com.softserveinc.booklibrary.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl extends AbstractEntityService<Review> implements ReviewService {

	@Autowired
	public ReviewServiceImpl(ReviewRepository reviewRepository) {
		repository = reviewRepository;
	}

	@Override
	public Review create(Review review) {
		if (review == null) {
			throw new NullEntityException();
		}
		Integer id = review.getReviewId();
		if (id != null) {
			throw new NotValidIdValueException(id);
		}
		return repository.create(review);
	}

	@Override
	public Review update(Review review) {
		if (review == null) {
			throw new NullEntityException();
		}
		Integer id = review.getReviewId();
		if (id == null || id <= 0) {
			throw new NotValidIdValueException(id);
		}
		return repository.update(review);
	}
}
