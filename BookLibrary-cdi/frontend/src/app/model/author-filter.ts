export class AuthorFilter {
    name: string
    ratingFrom: number
    ratingTo: number

    constructor(name: string, authorRatingFrom: number, authorRatingTo: number) {
        this.name = name;
        this.ratingFrom = authorRatingFrom;
        this.ratingTo = authorRatingTo;
    }
}