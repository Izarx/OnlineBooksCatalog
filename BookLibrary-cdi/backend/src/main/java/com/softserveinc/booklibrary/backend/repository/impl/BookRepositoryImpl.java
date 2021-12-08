package com.softserveinc.booklibrary.backend.repository.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.softserveinc.booklibrary.backend.dto.filtering.AuthorFilter;
import com.softserveinc.booklibrary.backend.dto.filtering.BookFilter;
import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.repository.BookRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends AbstractEntityRepository<Book, BookFilter> implements BookRepository {

	@Override
	public boolean isEntityValid(Book book) {
		String name = book.getName();
		Integer yearPublished = book.getYearPublished();
		String publisher = book.getPublisher();
		String isbn = book.getIsbn();   // todo: redundant variable
		if (!isbn.matches("[0-9]+")) {
			return false;
		}
		if (name == null || name.length() > Book.NAME_LENGTH) {
			return false;
		}
		if (yearPublished == null || yearPublished < 0 || yearPublished > LocalDate.now().getYear()) {
			return false;
		}
		return publisher != null && publisher.length() <= Book.PUBLISHER_LENGTH;
	}

	@Override
	protected void setOrdersByColumnsByDefault(List<Order> orderList,
	                                           CriteriaBuilder builder,
	                                           Root<Book> rootEntity) {
		orderList.add(builder.desc(rootEntity.get("bookRating")));
		orderList.add(builder.desc(rootEntity.get("createDate")));
	}


	@Override
	protected List<Predicate> getFilteringParams(RequestOptions<BookFilter> options,
	                                             CriteriaBuilder builder,
	                                             Root<Book> rootEntity) {
		// todo: the same problems as in AuthorRepositoryImpl.getFilteringParams

		List<Predicate> predicates = new ArrayList<>();
		BookFilter bookFilter = options.getFilteredEntity();
		String name = null;
		AuthorFilter authorFilter = null;
		BigDecimal bookRatingFrom = null;
		BigDecimal bookRatingTo = null;
		Integer year = null;
		String isbn = null;
		if (ObjectUtils.isNotEmpty(bookFilter)) {
			name = bookFilter.getName();
			authorFilter = bookFilter.getAuthorFilter();
			bookRatingFrom = bookFilter.getBookRatingFrom();
			bookRatingTo = bookFilter.getBookRatingTo();
			year = bookFilter.getYear();
			isbn = bookFilter.getIsbn();
		}
		if (StringUtils.isNotEmpty(name)) {
			predicates.add(builder.like(rootEntity.get("name"), '%' + name + '%'));
		}
		if (ObjectUtils.isNotEmpty(authorFilter) && StringUtils.isNotEmpty(authorFilter.getName())) {

			Join<Book, Author> bookAuthorJoin = rootEntity.join("authors");
			predicates.add(bookAuthorJoin.on(builder.or(builder.like(bookAuthorJoin.get("firstName"),
							'%' + authorFilter.getName() + '%'),
					builder.like(bookAuthorJoin.get("lastName"),
							'%' + authorFilter.getName() + '%'))).getOn());

		}
		if (ObjectUtils.isNotEmpty(bookRatingFrom) && ObjectUtils.isNotEmpty(bookRatingTo)) {
			predicates.add(builder.between(rootEntity.get("bookRating"), bookRatingFrom, bookRatingTo));
		} else if (ObjectUtils.isNotEmpty(bookRatingFrom) && ObjectUtils.isEmpty(bookRatingTo)) {
			predicates.add(builder.greaterThanOrEqualTo(rootEntity.get("bookRating"), bookRatingFrom));
		} else if (ObjectUtils.isEmpty(bookRatingFrom) && ObjectUtils.isNotEmpty(bookRatingTo)) {
			predicates.add(builder.lessThanOrEqualTo(rootEntity.get("bookRating"), bookRatingTo));
		}
		if (ObjectUtils.isNotEmpty(year)) {
			predicates.add(builder.equal(rootEntity.get("yearPublished"), year));
		}
		if (StringUtils.isNotEmpty(isbn)) {
			predicates.add(builder.like(rootEntity.get("isbn"), '%' + isbn + '%'));
		}
		return predicates;
	}
}
