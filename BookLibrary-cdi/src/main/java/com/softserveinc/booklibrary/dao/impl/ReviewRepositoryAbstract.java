package com.softserveinc.booklibrary.dao.impl;

import com.softserveinc.booklibrary.dao.ReviewRepository;
import com.softserveinc.booklibrary.entity.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryAbstract extends AbstractEntityRepository<Review> implements ReviewRepository {

	@Override
	public Review getById(Integer id) {
		Review review = entityManager.find(Review.class, id);
		return review;
	}
}
