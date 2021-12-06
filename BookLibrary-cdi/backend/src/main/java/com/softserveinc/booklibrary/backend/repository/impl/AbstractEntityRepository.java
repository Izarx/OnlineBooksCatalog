package com.softserveinc.booklibrary.backend.repository.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
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
import org.springframework.util.ReflectionUtils;

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
	public ResponseData<T> listEntities(RequestOptions<T> requestOptions) {
		ResponseData<T> responseData = new ResponseData<>();
		int pageSize = requestOptions.getPageSize();
		int pageNumber = requestOptions.getPageNumber();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();

		CriteriaQuery<T> criteriaQuery = builder.createQuery(type);
		Root<T> rootEntity = criteriaQuery.from(type);
		CriteriaQuery<T> selectEntities = criteriaQuery.select(rootEntity);

		List<Predicate> predicates = new ArrayList<>();

		if (ObjectUtils.isNotEmpty(requestOptions.getFilteredEntity())) {
			predicates = getFilteringParams(requestOptions, builder, rootEntity);
		}

		criteriaQuery.where(predicates.toArray(new Predicate[]{}));

		responseData.setTotalElements(getTotalElementsFromDb(builder, predicates));

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

	protected abstract void setOrdersByColumnsByDefault(List<Order> orderList,
	                                                    CriteriaBuilder builder,
	                                                    Root<T> rootEntity);

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

	/**
	 * Create list of predicates to obtain objects according to filtering options
	 * @param options request options which consists entity with filtering fields
	 * @param builder
	 * @param rootEntity
	 * @return
	 */
	protected List<Predicate> getFilteringParams(RequestOptions<T> options,
	                                           CriteriaBuilder builder,
	                                           Root<T> rootEntity){
		List<Predicate> predicates = new ArrayList<>();
		List<Field> fields = Arrays.stream(options.getFilteredEntity().getClass().getDeclaredFields())
				.filter(f -> !Modifier.isStatic(f.getModifiers()))
				.peek(f -> f.setAccessible(true))
				.collect(Collectors.toList());

		for (Field field : fields) {
			Object fieldValue = ReflectionUtils.getField(field, options.getFilteredEntity());
			if (ObjectUtils.isEmpty(fieldValue) && ObjectUtils.isEmpty(options.getRanges().get(field.getName()))) {
				continue;
			}
			if (fieldValue instanceof String) {
				predicates.add(builder.like(rootEntity.get(field.getName()),
						"%" + fieldValue + '%'));
			}
			else if (fieldValue instanceof Number) {
				if (fieldValue instanceof BigDecimal) {
					BigDecimal range = (BigDecimal) options.getRanges().get(field.getName());
					Path<BigDecimal> path = rootEntity.get(field.getName());
					if (ObjectUtils.isNotEmpty(range) && ObjectUtils.isNotEmpty(fieldValue)) {
						predicates.add(builder.between(path , (BigDecimal) fieldValue, range));
					}
					if (ObjectUtils.isEmpty(range) && ObjectUtils.isNotEmpty(fieldValue)) {
						predicates.add(builder.greaterThanOrEqualTo(path,(BigDecimal) fieldValue));
					}
				}
				else if (fieldValue instanceof Integer) {
					Integer range = (Integer) options.getRanges().get(field.getName());
					Path<Integer> path = rootEntity.get(field.getName());
					if (ObjectUtils.isNotEmpty(range) && ObjectUtils.isNotEmpty(fieldValue)) {
						predicates.add(builder.between(path , (Integer) fieldValue, range));
					}
					if (ObjectUtils.isEmpty(range) && ObjectUtils.isNotEmpty(fieldValue)) {
						predicates.add(builder.greaterThanOrEqualTo(path,(Integer) fieldValue));
					}
				}
			}
		}
		return predicates;
	}

	private Integer getTotalElementsFromDb (CriteriaBuilder builder, List<Predicate> predicates) {
		CriteriaQuery<Long> countCriteriaQuery = builder.createQuery(Long.class);
		countCriteriaQuery.select(builder.count(countCriteriaQuery.from(type)));
		entityManager.createQuery(countCriteriaQuery);
		countCriteriaQuery.where(predicates.toArray(new Predicate[]{}));
		return entityManager.createQuery(countCriteriaQuery).getSingleResult().intValue();
	}


}
