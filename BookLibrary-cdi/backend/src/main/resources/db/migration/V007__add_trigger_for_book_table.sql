CREATE TRIGGER authors_rating_delete_book
	AFTER DELETE
	ON public.authors_books
	FOR EACH ROW
EXECUTE FUNCTION public.calculate_rating_after_delete();

CREATE TRIGGER authors_rating_insert_update_book
	AFTER INSERT OR UPDATE
	ON public.authors_books
	FOR EACH ROW
EXECUTE FUNCTION public.calculate_rating();