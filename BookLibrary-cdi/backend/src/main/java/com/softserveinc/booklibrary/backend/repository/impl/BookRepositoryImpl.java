package com.softserveinc.booklibrary.backend.repository.impl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.softserveinc.booklibrary.backend.entity.Book;
import com.softserveinc.booklibrary.backend.repository.BookRepository;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepositoryImpl extends AbstractEntityRepository<Book> implements BookRepository {

	@Override
	public boolean isEntityValid(Book book) {
		String name = book.getName();
		Integer yearPublished = book.getYearPublished();
		String publisher = book.getPublisher();
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
}
