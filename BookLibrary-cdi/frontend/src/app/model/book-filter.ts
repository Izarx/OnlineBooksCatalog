import {AuthorFilter} from "./author-filter";

export class BookFilter {
    name: string;
    authorFilter: AuthorFilter;
    bookRatingFrom: number;
    bookRatingTo: number;
    year: number;
    isbn: string;

    constructor(name: string,
                authorFilter: AuthorFilter,
                bookRatingFrom: number,
                bookRatingTo: number,
                year: number,
                isbn: string)
    {
        this.name = name;
        this.authorFilter = authorFilter;
        this.bookRatingFrom = bookRatingFrom;
        this.bookRatingTo = bookRatingTo;
        this.year = year;
        this.isbn = isbn;
    }
}