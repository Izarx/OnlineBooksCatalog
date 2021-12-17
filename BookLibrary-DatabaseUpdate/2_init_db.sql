GRANT ALL PRIVILEGES ON DATABASE booklibrary TO booklibrary;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO booklibrary;

CREATE TABLE authors
(
	author_id   SERIAL PRIMARY KEY,
	first_name  VARCHAR(512) NOT NULL,
	last_name   VARCHAR(512),
	create_date TIMESTAMP DEFAULT NOW(),
	author_rating NUMERIC(3, 2)
		CONSTRAINT author_rating CHECK (author_rating >= 0 and author_rating <= 5) DEFAULT 0
);

CREATE TABLE books
(
	book_id        SERIAL PRIMARY KEY,
	name           VARCHAR(512) NOT NULL,
	year_published INTEGER      NOT NULL,
	isbn           VARCHAR(13) UNIQUE
		CONSTRAINT books_isbn NOT NULL,
	publisher      VARCHAR(256),
	create_date    TIMESTAMP DEFAULT NOW(),
	book_rating NUMERIC(3, 2)
		CONSTRAINT book_rating CHECK (book_rating >= 0 and book_rating <= 5) DEFAULT 0
);

CREATE TABLE authors_books
(
	author_id INTEGER
		CONSTRAINT authors_books_author_id_fkey
			REFERENCES authors ON DELETE CASCADE NOT NULL,
	book_id   INTEGER
		CONSTRAINT authors_books_book_id_fkey
			REFERENCES books ON DELETE CASCADE NOT NULL
);

CREATE TABLE reviews
(
	review_id      SERIAL PRIMARY KEY,
	commenter_name VARCHAR(256)                                      NOT NULL,
	comment        TEXT,
	rating         INTEGER
		CONSTRAINT reviews_rating CHECK (rating > 0 and rating <= 5) NOT NULL,
	create_date    TIMESTAMP DEFAULT NOW(),
	book_id        INTEGER
		CONSTRAINT book_fkey
			REFERENCES books ON DELETE CASCADE                       NOT NULL
);


CREATE OR REPLACE FUNCTION calculate_rating()
	RETURNS TRIGGER AS
$calculate_rating$
declare
    changed_book_id integer;
BEGIN

    if (tg_op = 'DELETE') then
	    changed_book_id = old.book_id;
	else
		changed_book_id = new.book_id;
    end if;

	update books b
	set book_rating = (
		select coalesce(avg(rating), 0)
		from reviews r
		where r.book_id = b.book_id
	)
	WHERE b.book_id = changed_book_id;

	update authors a
	set author_rating = (
		select coalesce(avg(book_rating), 0)
		from books
			     join authors_books bab on bab.book_id = books.book_id
		where bab.author_id = a.author_id and (select count(review_id) from reviews where reviews.book_id = books.book_id) > 0
	)
	from (
		     select authors.author_id
		     from authors
			          join authors_books aab on aab.author_id = authors.author_id
		     where aab.book_id = changed_book_id
	     ) as authos_to_update
	where authos_to_update.author_id = a.author_id;


	RETURN new;
END;
$calculate_rating$
	LANGUAGE 'plpgsql';

CREATE TRIGGER authors_books_rating
	AFTER INSERT OR UPDATE OR DELETE
	ON public.reviews
	FOR EACH ROW
EXECUTE FUNCTION public.calculate_rating();

CREATE TRIGGER authors_rating_delete_book
	BEFORE DELETE
	ON public.authors_books
	FOR EACH ROW
EXECUTE FUNCTION public.calculate_rating();

CREATE TRIGGER authors_rating_insert_update_book
	AFTER INSERT OR UPDATE
	ON public.authors_books
	FOR EACH ROW
EXECUTE FUNCTION public.calculate_rating();