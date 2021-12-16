package com.softserveinc.booklibrary.backend.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.entity.Review;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.filtering.ReviewFilter;
import com.softserveinc.booklibrary.backend.repository.ReviewRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewRepositoryImpl extends AbstractEntityRepository<Review, ReviewFilter> implements ReviewRepository {

	@Override
	public boolean isEntityValid(Review review) {
		String commenterName = review.getCommenterName();
		Integer rating = review.getRating();
		Book book = review.getBook();
		if (StringUtils.isEmpty(commenterName) || commenterName.length() > Review.COMMENTER_NAME_LENGTH) {
			return false;
		}
		if (rating == null || rating < 1 || rating > 5) {
			return false;
		}
		return book != null && book.getBookId() != null;
	}

	@Override
	protected void setOrdersByColumnsByDefault(List<Order> orderList, CriteriaBuilder builder, Root<Review> rootEntity) {
		orderList.add(builder.desc(rootEntity.get("createDate")));
	}

	@Override
	protected List<Predicate> getFilteringParams(RequestOptions<ReviewFilter> options, CriteriaBuilder builder, Root<Review> rootEntity) {
		List<Predicate> predicates = new ArrayList<>();
		ReviewFilter reviewFilter = options.getFilteredEntity();
		Integer bookId = null;
		if (ObjectUtils.isNotEmpty(reviewFilter)) {
			bookId = reviewFilter.getBookId();
		}
		if (bookId == null) {
			bookId = 0;
		}
		predicates.add(builder.equal(rootEntity.get("book"), bookId));
		return predicates;
	}

}
