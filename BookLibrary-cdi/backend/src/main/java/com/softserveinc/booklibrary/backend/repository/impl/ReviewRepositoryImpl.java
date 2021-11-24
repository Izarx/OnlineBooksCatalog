package com.softserveinc.booklibrary.backend.repository.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.entity.Review;
import com.softserveinc.booklibrary.backend.repository.BookRepository;
import com.softserveinc.booklibrary.backend.repository.ReviewRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl extends AbstractEntityRepository<Review> implements ReviewRepository {

	private final BookRepository bookRepository;

	public ReviewRepositoryImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public boolean isEntityValid(Review review) {
		String commenterName = review.getCommenterName();
		Integer rating = review.getRating();
		Book book = review.getBook();
		if (commenterName == null || commenterName.length() > Review.COMMENTER_NAME_LENGTH) {
			return false;
		}
		if (rating == null || rating < 1 || rating > 5) {
			return false;
		}
		return book != null && book.getBookId() != null;
	}

	@Override
	protected void setOrdersByColumnsByDefault(List<Order> orderList, CriteriaBuilder builder, Root<Review> rootEntity) {}
}
