package com.softserveinc.booklibrary.backend.repository.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.softserveinc.booklibrary.backend.entity.AbstractEntity;
import com.softserveinc.booklibrary.backend.exception.NotValidEntityException;
import com.softserveinc.booklibrary.backend.exception.NotValidIdException;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.ResponseData;
import com.softserveinc.booklibrary.backend.pagination.SortableColumn;
import com.softserveinc.booklibrary.backend.repository.EntityRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractEntityRepository<T extends AbstractEntity<? extends Serializable>> implements EntityRepository<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityRepository.class);
	private final Class<T> type;
	@PersistenceContext
	protected EntityManager entityManager;

	protected AbstractEntityRepository() {
		type = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public T create(T entity) {
		if (!isEntityValid(entity)) {
			throw new NotValidEntityException();
		}
		Serializable id = entity.getEntityId();
		if (id != null) {
			throw new NotValidIdException(id);
		}
		entityManager.persist(entity);
		return entity;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public T update(T entity) {
		if (!isEntityValid(entity)) {
			throw new NotValidEntityException();
		}
		Serializable id = entity.getEntityId();
		if (id == null || entityManager.find(type, id) == null) {
			throw new NotValidIdException(id);
		}
		return entityManager.merge(entity);
	}

	@Override
	public T getById(Serializable id) {
		if (id == null) {
			throw new NotValidIdException(null);
		}
		return entityManager.find(type, id);
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public boolean delete(Serializable id) {
		if (id == null) {
			return false;
		}
		T entity = entityManager.find(type, id);
		if (entity != null) {
			entityManager.remove(entity);
			return true;
		}
		return false;
	}

	@Override
	public abstract boolean isEntityValid(T entity);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<T> getAll() {
		CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(type);
		CriteriaQuery<T> getAll = criteriaQuery.select(criteriaQuery.from(type));
		return entityManager.createQuery(getAll).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ResponseData<T> listEntities(RequestOptions requestOptions) {
		ResponseData<T> responseData = new ResponseData<>();
		int pageSize = requestOptions.getPageSize();
		int pageNumber = requestOptions.getPageNumber();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		int totalElementsFromDb = getTotalElementsFromDb(builder);
		while (pageSize * pageNumber >= totalElementsFromDb) {
			pageNumber--;
		}
		responseData.setTotalElements(totalElementsFromDb);

		CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
		Root<T> rootEntity = criteriaQuery.from(type);
		CriteriaQuery<T> selectEntities = criteriaQuery.select(rootEntity);

		criteriaQuery.orderBy(setOrdersByColumns(requestOptions.getSorting(), builder, rootEntity));
		List<T> getAll = entityManager
				.createQuery(selectEntities)
				.setFirstResult(pageNumber * pageSize)
				.setMaxResults(pageSize)
				.getResultList();
		responseData.setContent(getAll);
		return responseData;
	}

	@Override
	public List<T> bulkDeleteEntities(List<Serializable> entitiesIdsForDelete) {
		Optional<Field> field = Stream.of(type.getFields()).filter(f -> f.isAnnotationPresent(Id.class)).findFirst();
		String columnName = field.orElseThrow().getName();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		List<T> unavailableToDeleteEntities = getUnavailableToDeleteEntities(entitiesIdsForDelete);
		if (CollectionUtils.isNotEmpty(unavailableToDeleteEntities)) {
			unavailableToDeleteEntities.forEach(entity -> entitiesIdsForDelete.remove(entity.getEntityId()));
		}
		CriteriaDelete<T> delete = builder.createCriteriaDelete(type);
		Root<T> rootDelete = delete.from(type);
		In<Serializable> inClause = builder.in(rootDelete.get(columnName));
		entitiesIdsForDelete.forEach(inClause::value);
		delete.where(inClause);
		entityManager.createQuery(delete).executeUpdate();

		return unavailableToDeleteEntities;
	}

	protected abstract List<T> getUnavailableToDeleteEntities(List<Serializable> entitiesIdsForDelete);

	/**
	 * Create List of Orders from sortable columns to order db entities by it
	 *
	 * @param sortableColumns columns, which we receive from UI, they consist from name field and direction for sorting
	 * @param builder
	 * @param rootEntity
	 * @return
	 */

	private List<Order> setOrdersByColumns(List<SortableColumn> sortableColumns,
	                                         CriteriaBuilder builder,
	                                         Root<T> rootEntity) {
		List<Order> orderList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(sortableColumns)) {
			for (SortableColumn column : sortableColumns) {
				if ("asc".equals(column.getDirection())) {
					orderList.add(builder.asc(rootEntity.get(column.getName())));
				}
				if ("desc".equals(column.getDirection())) {
					orderList.add(builder.desc(rootEntity.get(column.getName())));
				}
			}
		}
		// Default sorting for manage entities pages could be by creation date
		else {
			setOrdersByColumnsByDefault(orderList, builder, rootEntity);
		}
		return orderList;
	}

	protected abstract void setOrdersByColumnsByDefault(List<Order> orderList,
	                                                    CriteriaBuilder builder,
	                                                    Root<T> rootEntity);

	private Integer getTotalElementsFromDb (CriteriaBuilder builder) {
		CriteriaQuery<Long> countCriteriaQuery = builder.createQuery(Long.class);
		countCriteriaQuery.select(builder.count(countCriteriaQuery.from(type)));
		return entityManager.createQuery(countCriteriaQuery).getSingleResult().intValue();
	}


}
