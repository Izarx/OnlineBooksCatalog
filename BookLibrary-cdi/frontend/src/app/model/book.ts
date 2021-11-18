export class Book {
    bookId: number | null | undefined
    name: string
    yearPublished: number
    isbn: number
    publisher: string
    bookRating: number


    constructor(bookId: number, name: string, yearPublished: number, isbn: number, publisher: string, bookRating: number) {
        this.bookId = bookId;
        this.name = name;
        this.yearPublished = yearPublished;
        this.isbn = isbn;
        this.publisher = publisher;
        this.bookRating = bookRating;
    }
}
