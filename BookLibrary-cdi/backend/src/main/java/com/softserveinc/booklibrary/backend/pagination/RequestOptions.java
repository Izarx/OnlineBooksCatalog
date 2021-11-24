package com.softserveinc.booklibrary.backend.pagination;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RequestOptions {

	private int pageSize;
	private int pageNumber;
	private List<SortableColumn> sorting;

}
