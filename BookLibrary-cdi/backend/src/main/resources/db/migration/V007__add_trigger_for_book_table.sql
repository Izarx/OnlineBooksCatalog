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