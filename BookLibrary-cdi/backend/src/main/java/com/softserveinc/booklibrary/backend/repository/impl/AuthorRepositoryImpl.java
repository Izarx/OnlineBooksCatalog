package com.softserveinc.booklibrary.backend.repository.impl;

import java.io.Serializable;
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
	protected List<Predicate> getFilteringParams(RequestOptions<AuthorFilter> options, CriteriaBuilder builder, Root<Author> rootEntity) {
		return null;
	}
}
