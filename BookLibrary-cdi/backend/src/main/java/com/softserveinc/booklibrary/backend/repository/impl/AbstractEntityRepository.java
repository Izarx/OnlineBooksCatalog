package com.softserveinc.booklibrary.backend.repository.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.softserveinc.booklibrary.backend.entity.AbstractEntity;
import com.softserveinc.booklibrary.backend.exception.NotValidEntityException;
import com.softserveinc.booklibrary.backend.exception.NotValidIdException;
import com.softserveinc.booklibrary.backend.pagination.RequestOptions;
import com.softserveinc.booklibrary.backend.pagination.ResponseData;
import com.softserveinc.booklibrary.backend.pagination.SortableColumn;
import com.softserveinc.booklibrary.backend.repository.EntityRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractEntityRepository<T extends AbstractEntity<? extends Serializable>, V> implements EntityRepository<T, V> {
	// todo: must use LOGGER
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
		LOGGER.info("Start creating entity {} in repository", type);
		LOGGER.info("Entity before persist equals {}", entity);
		if (!isEntityValid(entity)) {
			throw new NotValidEntityException(entity);
		}
		Serializable id = entity.getEntityId();
		LOGGER.info("The ID of creating entity equals {}", id);
		if (id != null) {
			throw new NotValidIdException(id);
		}
		entityManager.persist(entity);
		LOGGER.info("Entity {} were persisted and equals {}", type, entity);
		return entity;
	}

	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public T update(T entity) {
		LOGGER.info("Start updating entity {} in repository", type);
		LOGGER.info("Entity {} before merge equals {}", type, entity);
		if (!isEntityValid(entity)) {
			throw new NotValidEntityException(entity);
		}
		Serializable id = entity.getEntityId();
		LOGGER.info("The ID of updating updating {} equals {}", type, id);
		if (id == null || entityManager.find(type, id) == null) {
			throw new NotValidIdException(id);
		}
		LOGGER.info("Entity {} were merged and equals {}", type, entity);
		return entityManager.merge(entity);
	}

	@Override
	public T getById(Serializable id) {
		LOGGER.info("The ID of getting entity {} equals {}", type, id);
		if (id == null) {
			throw new NotValidIdException(null);
		}
		T entity = entityManager.find(type, id);
		LOGGER.info("Entity {} which was found is {}", type, entity);
		return entity;
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
		return false;   // todo: why false?
	}

	@Override
	public abstract boolean isEntityValid(T entity); // todo: remove this method from here

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<T> getAll() {
		CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(type);
		CriteriaQuery<T> getAll = criteriaQuery.select(criteriaQuery.from(type)); // todo: redundant variable getAll
		return entityManager.createQuery(getAll).getResultList();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public ResponseData<T> listEntities(RequestOptions<V> requestOptions) {
		ResponseData<T> responseData = new ResponseData<>();
		int pageSize = requestOptions.getPageSize();
		int pageNumber = requestOptions.getPageNumber();    // todo: redundant variable pageNumber
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
		Root<T> rootEntity = criteriaQuery.from(type);
		CriteriaQuery<T> selectEntities = criteriaQuery.select(rootEntity); // todo: redundant variable selectEntities

		List<Predicate> predicates = new ArrayList<>(); // todo: redundant variable here - move it inside 'if'

		// todo: why this check here? Need to do this check inside getFilteringParams
		if (ObjectUtils.isNotEmpty(requestOptions.getFilteredEntity())) { // todo: why is using ObjectUtils.isNotEmpty instead of simple null check?
			predicates = getFilteringParams(requestOptions, builder, rootEntity);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[]{}));     // todo move inside 'if'

		responseData.setTotalElements(getCountEntities(requestOptions, builder));

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
	@Transactional(propagation = Propagation.MANDATORY)
	public List<T> bulkDeleteEntities(List<Serializable> entitiesIdsForDelete) {
		String columnName = Stream.of(type.getDeclaredFields())
				.filter(f -> f.isAnnotationPresent(Id.class))
				.findFirst()
				.orElseThrow()
				.getName();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
		CriteriaDelete<T> criteriaDelete = builder.createCriteriaDelete(type);
		Root<T> rootDelete = criteriaDelete.from(type);

		List<T> unavailableToDeleteEntities =
				getUnavailableToDeleteEntities(entitiesIdsForDelete, criteriaQuery);
		if (CollectionUtils.isNotEmpty(unavailableToDeleteEntities)) {
			unavailableToDeleteEntities
					.forEach(entity -> entitiesIdsForDelete.remove(entity.getEntityId()));
		}
		criteriaDelete.where(rootDelete.get(columnName).in(entitiesIdsForDelete));
		entityManager.createQuery(criteriaDelete).executeUpdate();

		return unavailableToDeleteEntities;
	}

	protected List<T> getUnavailableToDeleteEntities(List<Serializable> entitiesIdsForDelete,
	                                                 CriteriaQuery<T> criteriaQuery) {
		return Collections.emptyList();
	}

	protected void setOrdersByColumnsByDefault(List<Order> orderList, CriteriaBuilder builder,
	                                                    Root<T> rootEntity) {}

	/**
	 * Create List of Orders from sortable columns to order db entities by it
	 *
	 * @param sortableColumns columns, which we receive from UI, they consist from name field and direction for sorting
	 * @param builder         criteria builder
	 * @param rootEntity      root
	 * @return list of sorting orders for each sortable column which was activated
	 */

	private List<Order> setOrdersByColumns(List<SortableColumn> sortableColumns, CriteriaBuilder builder,
	                                       Root<T> rootEntity) {
		List<Order> orderList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(sortableColumns)) {
			for (SortableColumn column : sortableColumns) {
				if ("asc".equals(column.getDirection())) {  // todo: why still is using string for "asc" and "desc"? Enum!
					orderList.add(builder.asc(rootEntity.get(column.getName())));
				}
				else if ("desc".equals(column.getDirection())) {
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

	/**
	 * Create list of predicates to obtain objects according to filtering options
	 *
	 * @param options    request options which consists entity with filtering fields
	 * @param builder    criteria builder
	 * @param rootEntity root
	 * @return list of predicates according to request options
	 */
	protected List<Predicate> getFilteringParams(RequestOptions<V> options,
	                                             CriteriaBuilder builder,
	                                             Root<T> rootEntity) {
		return Collections.emptyList();
	}

	protected Integer getCountEntities(RequestOptions<V> options,    // todo: please rename this method to more understandable name
	                                   CriteriaBuilder builder) {
		CriteriaQuery<Long> countCriteriaQuery = builder.createQuery(Long.class);   // todo: not use Long here
		Root<T> rootEntity = countCriteriaQuery.from(type);
		countCriteriaQuery.select(builder.count(rootEntity));

		// todo: why this check here? Need to do this check inside getFilteringParams
		if (ObjectUtils.isNotEmpty(options.getFilteredEntity())) {  // todo: why is using ObjectUtils.isNotEmpty instead of simple null check?
			entityManager.createQuery(countCriteriaQuery);  // todo what is reason?
			countCriteriaQuery.where(getFilteringParams(options, builder, rootEntity).toArray(new Predicate[]{}));
		}

		return entityManager.createQuery(countCriteriaQuery).getSingleResult().intValue();
	}


}
