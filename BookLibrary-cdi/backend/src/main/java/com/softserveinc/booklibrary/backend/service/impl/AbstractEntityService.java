package com.softserveinc.booklibrary.backend.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import com.softserveinc.booklibrary.backend.dto.paging.MyPage;
import com.softserveinc.booklibrary.backend.dto.paging.MyPageable;
import com.softserveinc.booklibrary.backend.entity.MyAppEntity;
import com.softserveinc.booklibrary.backend.exception.NotValidEntityException;
import com.softserveinc.booklibrary.backend.exception.NotValidIdException;
import com.softserveinc.booklibrary.backend.repository.EntityRepository;
import com.softserveinc.booklibrary.backend.service.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractEntityService<T extends MyAppEntity<? extends Serializable>> implements EntityService<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityService.class);
	protected EntityRepository<T> repository;

	@Override
	@Transactional
	public T create(T entity) {
		if (!repository.isEntityValid(entity)) {
			throw new NotValidEntityException();
		}
		Serializable id = entity.getEntityId();
		if (id != null) {
			throw new NotValidIdException(id);
		}
		return repository.create(entity);
	}

	@Override
	@Transactional
	public T update(T entity) {
		if (!repository.isEntityValid(entity)) {
			throw new NotValidEntityException();
		}
		Serializable id = entity.getEntityId();
		if (id == null || repository.getById(id) == null) {
			throw new NotValidIdException(id);
		}
		return repository.update(entity);
	}

	@Override
	public T getById(Serializable id) {
		if (id == null) {
			throw new NotValidIdException(null);
		}
		return repository.getById(id);
	}

	@Override
	@Transactional
	public boolean delete(Serializable id) {
		if (id == null) {
			return false;
		}
		return repository.delete(id);
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<T> getAll() {
		return repository.getAll();
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public MyPage<T> getAllPageableAndSortable(int pageId, int numEntitiesOnPage) {
		MyPage<T> page = new MyPage<>();
		List<T> allEntities = getAll();
		int totalElements = allEntities.size();
		int totalPages = totalElements/numEntitiesOnPage + 1;
		page.setTotalElements(totalElements);
		page.setTotalPages(totalPages);
		if (pageId + 1 > totalPages || pageId < 0) {
			pageId = 0;
		}
		setFirstLastNumElements(pageId, numEntitiesOnPage, page);
		page.setPageable(new MyPageable(numEntitiesOnPage, pageId));
		page.setContent(getAll().stream()
				.skip(pageId *numEntitiesOnPage)
				.limit((pageId + 1) *numEntitiesOnPage).collect(Collectors.toList()));
		return page;
	}

	private void setFirstLastNumElements(int pageId, int numEntitiesOnPage, MyPage<T> page) {
		if (page.getTotalPages() == 1) {
			page.setFirst(true);
			page.setLast(true);
			page.setNumberOfFirstPageElement(1);
			page.setNumberOfElements(page.getTotalElements());
		}
		else if (pageId == 0) {
			page.setFirst(true);
			page.setLast(false);
			page.setNumberOfFirstPageElement(1);
			page.setNumberOfElements(numEntitiesOnPage);
		}
		else if (pageId + 1 == page.getTotalPages()) {
			page.setLast(true);
			page.setFirst(false);
			page.setNumberOfFirstPageElement(pageId*numEntitiesOnPage + 1);
			page.setNumberOfElements(page.getTotalElements());
		}
		else {
			page.setFirst(false);
			page.setLast(false);
			page.setNumberOfFirstPageElement(pageId*numEntitiesOnPage + 1);
			page.setNumberOfElements(pageId*numEntitiesOnPage + (pageId + 1) *numEntitiesOnPage);
		}
	}

}
