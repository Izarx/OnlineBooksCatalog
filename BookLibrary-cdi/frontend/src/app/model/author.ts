import {Book} from "./book";

export class Author {
    authorId: number | null | undefined
    firstName: string
    lastName: string
    authorRating: number
    books? : Book[]
    isChecked?: boolean

    constructor(authorId: number, firstName: string, lastName: string, authorRating: number) {
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorRating = authorRating;
        this.books = [];
        this.isChecked = false
    }
}
