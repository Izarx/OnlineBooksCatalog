package com.softserveinc.booklibrary.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book implements AbstractEntity<Integer> {

	public static final int NAME_LENGTH = 512;
	public static final int PUBLISHER_LENGTH = 256;
	private static final long serialVersionUID = -7525103935413749524L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id", nullable = false)
	private Integer bookId;

	@Column(name = "name", nullable = false, length = NAME_LENGTH)
	private String name;

	@Column(name = "year_published", nullable = false)
	private Integer yearPublished;

	@Column(name = "isbn", nullable = false, unique = true)
	private Long isbn;

	@Column(name = "publisher", length = PUBLISHER_LENGTH)
	private String publisher;

	@Column(name = "create_date", updatable = false, insertable = false)
	private LocalDateTime createDate;

	@Column(name = "book_rating")
	private BigDecimal bookRating;

	@ManyToMany(mappedBy = "books")
	private Set<Author> authors;

	@Override
	public Integer getEntityId() {
		return bookId;
	}
}
