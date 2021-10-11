package com.softserveinc.booklibrary.service.impl;

import com.softserveinc.booklibrary.dao.ReviewRepository;
import com.softserveinc.booklibrary.entity.Review;
import com.softserveinc.booklibrary.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

	private final ReviewRepository reviewRepository;

	@Autowired
	public ReviewServiceImpl(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}

	@Override
	public Review create(Review review) {
		return reviewRepository.save(review).orElseThrow();
	}

	@Override
	public Review getById(Integer id) {
		logger.info("******************Service method getById****************");
		return reviewRepository.getById(id).orElseThrow();
	}

	@Override
	public Review update(Review review) {
		reviewRepository.getById(review.getReviewId())
				.ifPresent(reviewRepository::save);
		return review;
	}

	@Override
	public void delete(Integer id) {
		reviewRepository.delete(getById(id));
	}
}
