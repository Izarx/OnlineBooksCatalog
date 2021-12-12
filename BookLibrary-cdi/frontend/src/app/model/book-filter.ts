export class BookFilter {
    name: string;
    authorName: string;
    ratingFrom: number;
    ratingTo: number;
    year: number;
    isbn: string;

    constructor(name: string,
                authorName: string,
                bookRatingFrom: number,
                bookRatingTo: number,
                year: number,
                isbn: string) {
        this.name = name;
        this.authorName = authorName;
        this.ratingFrom = bookRatingFrom;
        this.ratingTo = bookRatingTo;
        this.year = year;
        this.isbn = isbn;
    }
}