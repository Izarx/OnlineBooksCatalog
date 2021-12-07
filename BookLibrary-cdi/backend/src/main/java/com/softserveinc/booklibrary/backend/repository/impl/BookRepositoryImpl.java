package com.softserveinc.booklibrary.backend.repository.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.softserveinc.booklibrary.backend.dto.filtering.BookFilter;
import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.repository.BookRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends AbstractEntityRepository<Book, BookFilter> implements BookRepository {

	@Override
	public boolean isEntityValid(Book book) {
		String name = book.getName();
		Integer yearPublished = book.getYearPublished();
		String publisher = book.getPublisher();
		String isbn = book.getIsbn();
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
		return null;
	}
}
