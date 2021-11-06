package com.softserveinc.booklibrary.backend.dto.paging;

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

}
