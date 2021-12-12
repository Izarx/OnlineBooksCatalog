package com.softserveinc.booklibrary.backend.service.impl;

import com.softserveinc.booklibrary.backend.pagination.filtering.ReviewFilter;
import com.softserveinc.booklibrary.backend.entity.Review;
import com.softserveinc.booklibrary.backend.repository.ReviewRepository;
import com.softserveinc.booklibrary.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl extends AbstractEntityService<Review, ReviewFilter> implements ReviewService {

	@Autowired
	public ReviewServiceImpl(ReviewRepository reviewRepository) {
		repository = reviewRepository;
	}

}
