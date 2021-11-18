package com.softserveinc.booklibrary.backend.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Getter
@Setter
@Entity
@Table(name = "authors")
public class Author implements AbstractEntity<Integer> {

	public static final int FIRST_NAME_LENGTH = 512;
	public static final int LAST_NAME_LENGTH = 512;
	private static final long serialVersionUID = -8985579575202450002L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "author_id", nullable = false)
	private Integer authorId;

	@Column(name = "first_name", nullable = false, length = FIRST_NAME_LENGTH)
	private String firstName;

	@Column(name = "last_name", length = LAST_NAME_LENGTH)
	private String lastName;

	@Column(name = "create_date", updatable = false, insertable = false)
	private LocalDateTime createDate;

	@Column(name = "author_rating")
	private Double authorRating;

	@ManyToMany
	@JoinTable(
			name = "authors_books",
			joinColumns = {@JoinColumn(name = "author_id")},
			inverseJoinColumns = {@JoinColumn(name = "book_id")}
	)
	private Set<Book> books;

	@Override
	public Integer getEntityId() {
		return authorId;
	}
}