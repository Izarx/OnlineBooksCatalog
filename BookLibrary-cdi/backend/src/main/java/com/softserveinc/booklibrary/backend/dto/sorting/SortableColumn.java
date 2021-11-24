package com.softserveinc.booklibrary.backend.dto.sorting;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortableColumn {

	private String name;
	private String title;
	private String direction;

}
