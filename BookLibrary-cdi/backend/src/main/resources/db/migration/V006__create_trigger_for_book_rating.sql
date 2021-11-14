CREATE OR REPLACE FUNCTION calculate_rating()
 RETURNS TRIGGER AS
    $calculate_rating$
    BEGIN
	    update books b set book_rating = (
	        select avg(rating) from reviews r where r.book_id = b.book_id
		    )
	    WHERE b.book_id = new.book_id;

	    update authors a set author_rating = (
	        select avg(book_rating)
	        from books join authors_books ab on ab.book_id = books.book_id
	        where ab.author_id = a.author_id
		    )
	    from (
		    select authors.author_id
		    from authors
			join authors_books on authors_books.author_id = authors.author_id
		    where authors_books.book_id = new.book_id
		    ) as aabai
	    where aabai.author_id = a.author_id;

    RETURN new;
    END;
    $calculate_rating$
	LANGUAGE 'plpgsql';

CREATE TRIGGER books_rating
	AFTER INSERT
	ON public.reviews
	FOR EACH ROW
EXECUTE FUNCTION public.calculate_rating();