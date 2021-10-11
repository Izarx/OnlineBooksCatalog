package com.softserveinc.booklibrary.service;

import com.softserveinc.booklibrary.entity.Review;

public interface ReviewService {

	Review create(Review review);

	Review getById(Integer id);

	Review update(Review review);

	void delete(Integer id);
}
