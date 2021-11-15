ALTER TABLE books
	ADD COLUMN book_rating NUMERIC(3, 2)
		CONSTRAINT book_rating CHECK (book_rating >= 0 and book_rating <= 5) DEFAULT 0;

ALTER TABLE authors
	ADD COLUMN author_rating NUMERIC(3, 2)
		CONSTRAINT author_rating CHECK (author_rating >= 0 and author_rating <= 5) DEFAULT 0;