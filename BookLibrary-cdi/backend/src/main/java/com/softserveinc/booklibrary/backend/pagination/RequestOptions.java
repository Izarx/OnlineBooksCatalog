package com.softserveinc.booklibrary.backend.pagination;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestOptions<V> {

	private int pageSize;
	private int pageNumber;
	private List<SortableColumn> sorting;
	private V filteredEntity;

}
