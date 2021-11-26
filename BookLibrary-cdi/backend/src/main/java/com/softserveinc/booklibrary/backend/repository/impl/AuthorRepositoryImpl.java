package com.softserveinc.booklibrary.backend.repository.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.softserveinc.booklibrary.backend.entity.Author;
import com.softserveinc.booklibrary.backend.repository.AuthorRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorRepositoryImpl extends AbstractEntityRepository<Author> implements AuthorRepository {

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
	protected List<Author> getUnavailableToDeleteEntities(List<Serializable> entitiesIdsForDelete) {
		String sql = "SELECT a " +
				"FROM authors a JOIN authors_books ab on a.author_id = ab.author_id " +
				"WHERE a.author_id IN (:entitiesIdsForDelete)";
		return entityManager.createQuery(sql, Author.class).getResultList();
	}

	@Override
	protected void setOrdersByColumnsByDefault(List<Order> orderList,
	                                                  CriteriaBuilder builder,
	                                                  Root<Author> rootEntity) {
		orderList.add(builder.desc(rootEntity.get("authorRating")));
		orderList.add(builder.desc(rootEntity.get("createDate")));
	}
}
