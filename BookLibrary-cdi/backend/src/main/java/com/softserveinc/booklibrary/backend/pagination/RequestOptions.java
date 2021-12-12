package com.softserveinc.booklibrary.backend.pagination;


import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestOptions<V> {

	private int pageSize;
	private int pageNumber;
	private List<SortableColumn> sorting;
	private V filteredEntity;

}
