import {Book} from "./book";

export class Review {
    reviewId: number;
    commenterName: string;
    comment: string;
    rating: number;
    book: Book;

    constructor(reviewId: number,
                commenterName: string,
                comment: string,
                rating: number,
                book: Book
    ) {
        this.reviewId = reviewId;
        this.commenterName = commenterName;
        this.comment = comment;
        this.rating = rating;
        this.book = book;
    }
}