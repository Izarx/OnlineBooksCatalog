package com.softserveinc.booklibrary.backend.dto.paging;

import java.util.List;

import com.softserveinc.booklibrary.backend.dto.sorting.SortableColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MyPageable {

	private int pageSize;
	private int pageNumber;
	private List<SortableColumn> sortableColumns;

}
