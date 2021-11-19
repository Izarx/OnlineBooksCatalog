package com.softserveinc.booklibrary.backend.dto.sorting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SortableColumn {

	private String name;
	private String title;
	private String direction;

}
