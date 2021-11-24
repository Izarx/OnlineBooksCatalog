package com.softserveinc.booklibrary.backend.dto.paging;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationResponsePage<T> {

	private List<T> content;
	private Integer totalElements;

}
