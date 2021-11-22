CREATE TRIGGER change_rating
	AFTER DELETE OR INSERT
	ON public.authors_books
	FOR EACH ROW
EXECUTE FUNCTION public.calculate_rating();