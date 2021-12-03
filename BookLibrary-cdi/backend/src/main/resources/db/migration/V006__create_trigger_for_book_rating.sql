CREATE OR REPLACE FUNCTION calculate_rating()
 RETURNS TRIGGER AS
    $calculate_rating$
    BEGIN
	    update books b set book_rating = (
	        select coalesce(avg(rating), 0)
	        from reviews r
	        where r.book_id = b.book_id
		    )
	    WHERE b.book_id = new.book_id;

	    update authors a
	    set author_rating = (
		    select coalesce(avg(book_rating), 0)
	        from books
	            join authors_books bab on bab.book_id = books.book_id
	        where bab.author_id = a.author_id
		    )
	    from (
		    select authors.author_id
		    from authors
		        join authors_books aab on aab.author_id = authors.author_id
		    where aab.book_id = new.book_id
		    ) as authos_to_update
	    where authos_to_update.author_id = a.author_id;



    RETURN new;
    END;
    $calculate_rating$
	LANGUAGE 'plpgsql';

CREATE TRIGGER books_rating
	AFTER INSERT
	ON public.reviews
	FOR EACH ROW
EXECUTE FUNCTION public.calculate_rating();