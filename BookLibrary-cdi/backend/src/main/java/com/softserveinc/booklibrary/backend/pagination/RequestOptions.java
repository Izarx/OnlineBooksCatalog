package com.softserveinc.booklibrary.backend.pagination;


import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestOptions<T> {

	private int pageSize;
	private int pageNumber;
	private List<SortableColumn> sorting;
	private T filteredEntity;
	private Map<String, Number> ranges;

}
