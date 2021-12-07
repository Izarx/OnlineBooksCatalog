export class AuthorFilter {
    name: string
    authorRatingFrom: number
    authorRatingTo: number

    constructor(name: string, authorRatingFrom: number, authorRatingTo: number) {
        this.name = name;
        this.authorRatingFrom = authorRatingFrom;
        this.authorRatingTo = authorRatingTo;
    }
}