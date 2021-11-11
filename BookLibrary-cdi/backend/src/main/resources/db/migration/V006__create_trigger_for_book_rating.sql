CREATE OR REPLACE FUNCTION books_calculate_rating()
 RETURNS TRIGGER AS
    $books_calculate_rating$
    DECLARE
        count_ratings INTEGER;
        new_rating NUMERIC(3, 2);
        old_rating NUMERIC(3, 2);
    BEGIN
        IF (tg_op = 'INSERT') THEN
	        count_ratings := (SELECT count(review_id) FROM reviews WHERE reviews.book_id = new.book_id group by reviews.book_id);
	        old_rating := (SELECT book_rating FROM books WHERE book_id = new.book_id);
	        new_rating := (old_rating * (count_ratings - 1) + new.rating)/(count_ratings);
	        UPDATE books SET book_rating = new_rating WHERE book_id = new.book_id;
	    ELSEIF (tg_op = 'DELETE') THEN
		    RAISE EXCEPTION 'Delete review forbidden : %',
			    old;
        ELSEIF (tg_op = 'UPDATE') THEN
			RAISE EXCEPTION 'Change review forbidden : % -> %',
				old, new;
	    END IF;
    RETURN new;
    END;
    $books_calculate_rating$
	LANGUAGE 'plpgsql';

CREATE TRIGGER books_rating
	AFTER INSERT OR DELETE OR UPDATE OF rating
	ON public.reviews
	FOR EACH ROW
EXECUTE FUNCTION public.books_calculate_rating();