package com.softserveinc.booklibrary.backend.dto.filtering;

import java.math.BigDecimal;
import java.util.List;

import com.softserveinc.booklibrary.backend.entity.Author;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookFilter {

	private String name;
	private List<Author> authors;
	private BigDecimal bookRatingFrom;
	private BigDecimal bookRatingTo;
	private Integer year;
	private String isbn;

}
