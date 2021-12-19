package com.softserveinc.booklibrary.backend.repository.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.filtering.AuthorFilter;
import com.softserveinc.booklibrary.backend.repository.AuthorRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryImpl extends AbstractEntityRepository<Author, AuthorFilter> implements AuthorRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorRepositoryImpl.class);

	@Override
	public boolean isEntityValid(Author author) {
		String firstName = author.getFirstName();
		if (firstName != null) {
			firstName = firstName.trim();
			author.setFirstName(firstName);
		}
		return StringUtils.length(firstName) > 0 && StringUtils.length(firstName) <= Author.FIRST_NAME_LENGTH;
	}

	@Override
	protected List<Author> getUnavailableToDeleteEntities(List<Serializable> entitiesIdsForDelete,
	                                                      CriteriaQuery<Author> criteriaQuery) {
		Root<Author> root = criteriaQuery.from(Author.class);
		root.join("books");
		criteriaQuery.select(root).where(root.get("authorId").in(entitiesIdsForDelete));
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	protected void setOrdersByColumnsByDefault(List<Order> orderList,
	                                           CriteriaBuilder builder,
	                                           Root<Author> rootEntity) {
		orderList.add(builder.desc(rootEntity.get("authorRating")));
		orderList.add(builder.desc(rootEntity.get("createDate")));
	}

	@Override
	protected List<Predicate> getFilteringParams(RequestOptions<AuthorFilter> options,
	                                             CriteriaBuilder builder,
	                                             Root<Author> rootEntity) {
		List<Predicate> predicates = new ArrayList<>();
		if (options == null) {
			return predicates;
		}
		AuthorFilter authorFilter = options.getFilteredEntity();
		String name = null;
		BigDecimal authorRatingFrom = null;
		BigDecimal authorRatingTo = null;
		if (authorFilter != null) {
			name = authorFilter.getName();
			authorRatingFrom = authorFilter.getRatingFrom();
			authorRatingTo = authorFilter.getRatingTo();
		}
		if (StringUtils.isNotEmpty(name)) {
			Predicate predicateFirstName = builder.like(rootEntity.get("firstName"),
					'%' + name + '%');
			Predicate predicateLastName = builder.like(rootEntity.get("lastName"),
					'%' + name + '%');
			predicates.add(builder.or(predicateFirstName, predicateLastName));
		}
		if (authorRatingFrom != null) {
			predicates.add(builder.greaterThanOrEqualTo(rootEntity.get("authorRating"), authorRatingFrom));
		}
		if (authorRatingTo != null) {
			predicates.add(builder.lessThanOrEqualTo(rootEntity.get("authorRating"), authorRatingTo));
		}
		return predicates;
	}

}
