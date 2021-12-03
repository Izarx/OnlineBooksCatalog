package com.softserveinc.booklibrary.backend.pagination;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class RequestOptions<T> {

	private int pageSize;
	private int pageNumber;
	private List<SortableColumn> sorting;
	private T filteredEntity;

}
