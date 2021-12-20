CREATE OR REPLACE FUNCTION calculate_rating()
	RETURNS TRIGGER AS
$calculate_rating$
BEGIN

	update books b
	set book_rating = (
		select coalesce(avg(rating), 0)
		from reviews r
		where r.book_id = b.book_id
	)
	WHERE b.book_id = new.book_id;

	update authors au
	set author_rating = (
		select coalesce(avg(book_rating), 0)
		from books
			     join authors_books bab on bab.book_id = books.book_id
		where au.author_id = bab.author_id and (select count(review_id) from reviews where reviews.book_id = books.book_id) > 0
	)
	from (
		     select authors.author_id
		     from authors
			          join authors_books aab on aab.author_id = authors.author_id
		     where aab.book_id = new.book_id
	     ) as authos_to_update
	where au.author_id = authos_to_update.author_id;

	RETURN new;
END;
$calculate_rating$
	LANGUAGE 'plpgsql';


CREATE OR REPLACE FUNCTION calculate_rating_after_delete()
	RETURNS TRIGGER AS
$calculate_rating_after_delete$
BEGIN
	update authors au
	set author_rating = (
		select coalesce(avg(book_rating), 0)
		from books
			     join authors_books bab on bab.book_id = books.book_id
		where bab.author_id = au.author_id and bab.book_id != old.book_id
	)
	from authors
	where au.author_id = old.author_id;

	RETURN old;
END;
$calculate_rating_after_delete$
	LANGUAGE 'plpgsql';


CREATE TRIGGER books_rating
	AFTER INSERT OR UPDATE OR DELETE
	ON public.reviews
	FOR EACH ROW
EXECUTE FUNCTION public.calculate_rating();