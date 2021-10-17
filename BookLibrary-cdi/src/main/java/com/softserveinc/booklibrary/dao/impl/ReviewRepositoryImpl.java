package com.softserveinc.booklibrary.dao.impl;

import javax.persistence.EntityNotFoundException;

import com.softserveinc.booklibrary.dao.ReviewRepository;
import com.softserveinc.booklibrary.entity.Review;
import com.softserveinc.booklibrary.exception.NotValidIdValueException;
import com.softserveinc.booklibrary.exception.NullEntityException;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl extends AbstractEntityRepository<Review> implements ReviewRepository {
	@Override
	public Review create(Review review) {
		if (review == null) {
			throw new NullEntityException();
		}
		Integer id = review.getReviewId();
		if (id != null) {
			throw new NotValidIdValueException(id);
		}
		entityManager.persist(review);
		return review;
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
		if (entityManager.find(Review.class, id) == null) {
			throw new EntityNotFoundException(review.getClass() + " with ID = " + id + " not found!");
		}
		return entityManager.merge(review);
	}
}
