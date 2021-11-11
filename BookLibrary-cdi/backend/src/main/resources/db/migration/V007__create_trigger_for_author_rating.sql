CREATE OR REPLACE FUNCTION authors_calculate_rating()
	RETURNS TRIGGER AS
$authors_calculate_rating$
BEGIN
	drop table if exists authors_of_book;

	create temp table authors_of_book as
	select authors.author_id, count(authors_books.book_id) as count_books
	from authors
		     join authors_books on authors_books.author_id = authors.author_id
	where authors_books.book_id = new.book_id
	group by authors.author_id;

	UPDATE authors SET author_rating =
	    (authors_of_book.count_books * author_rating - old.book_rating + new.book_rating)/authors_of_book.count_books
    from authors_of_book
	where authors_of_book.author_id = authors.author_id;

	return new;
END;
$authors_calculate_rating$
	LANGUAGE plpgsql;

CREATE TRIGGER authors_rating
	AFTER INSERT OR DELETE OR UPDATE OF book_rating
	ON public.books
	FOR EACH ROW
EXECUTE FUNCTION public.authors_calculate_rating();