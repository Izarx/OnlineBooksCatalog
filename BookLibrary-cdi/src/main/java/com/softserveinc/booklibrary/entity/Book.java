package com.softserveinc.booklibrary.entity;

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
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book implements EntityLibrary<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id", nullable = false)
	private Integer bookId;

	@Column(name = "name", nullable = false, length = 512)
	private String name;

	@Column(name = "year_published", nullable = false)
	private Integer yearPublished;

	@Column(name = "isbn", nullable = false, unique = true)
	private Long isbn;

	@Column(name = "publisher", length = 256)
	private String publisher;

	@CreationTimestamp  // date created by creating instance forbidden for update and insert
	@Column(name = "create_date")
	private LocalDateTime createDate;

	@ManyToMany(mappedBy = "books")
	private Set<Author> authors;

	@Override
	public Integer getEntityId() {
		return bookId;
	}
}
