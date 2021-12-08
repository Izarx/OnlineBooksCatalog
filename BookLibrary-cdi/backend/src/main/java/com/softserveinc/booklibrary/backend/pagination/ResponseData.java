package com.softserveinc.booklibrary.backend.pagination;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseData<T> {

	private List<T> content;
	private Integer totalElements;  // todo: is really need Integer? maybe int?

}
