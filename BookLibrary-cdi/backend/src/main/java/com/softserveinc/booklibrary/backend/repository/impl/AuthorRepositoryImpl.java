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

import com.softserveinc.booklibrary.backend.dto.filtering.AuthorFilter;
import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.repository.AuthorRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryImpl extends AbstractEntityRepository<Author, AuthorFilter> implements AuthorRepository {

	@Override
	public boolean isEntityValid(Author author) {
		String firstName = author.getFirstName();
		if (firstName == null || firstName.length() > Author.FIRST_NAME_LENGTH) {
			return false;
		}
		String lastName = author.getLastName();
		return lastName != null && lastName.length() <= Author.LAST_NAME_LENGTH;
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
		AuthorFilter authorFilter = options.getFilteredEntity();
		String name = null;
		BigDecimal authorRatingFrom = null;
		BigDecimal authorRatingTo = null;
		if (ObjectUtils.isNotEmpty(authorFilter)) {
			name = authorFilter.getName();
			authorRatingFrom = authorFilter.getAuthorRatingFrom();
			authorRatingTo = authorFilter.getAuthorRatingTo();
		}
		if (StringUtils.isNotEmpty(name)) {
			Predicate predicateFirstName =  builder.like(rootEntity.get("firstName"),
					'%' + name + '%');
			Predicate predicateLastName =  builder.like(rootEntity.get("lastName"),
					'%' + name + '%');
			predicates.add(builder.or(predicateFirstName, predicateLastName));
		}
		if (ObjectUtils.isNotEmpty(authorRatingFrom) && ObjectUtils.isNotEmpty(authorRatingTo)) {
			predicates.add(builder.between(rootEntity.get("authorRating"), authorRatingFrom, authorRatingTo));
		}
		else if (ObjectUtils.isNotEmpty(authorRatingFrom) && ObjectUtils.isEmpty(authorRatingTo)){
			predicates.add(builder.greaterThanOrEqualTo(rootEntity.get("authorRating"),authorRatingFrom));
		}
		else if (ObjectUtils.isEmpty(authorRatingFrom) && ObjectUtils.isNotEmpty(authorRatingTo)) {
			predicates.add(builder.lessThanOrEqualTo(rootEntity.get("authorRating"),authorRatingTo));
		}
		return predicates;
	}
}
