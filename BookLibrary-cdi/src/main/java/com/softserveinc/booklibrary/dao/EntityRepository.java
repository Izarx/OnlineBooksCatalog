package com.softserveinc.booklibrary.dao;

import java.util.Optional;

public interface EntityRepository<T> {

	Optional<T> save(T t);

	Optional<T> getById(Integer id);

	void delete(T t);

}
