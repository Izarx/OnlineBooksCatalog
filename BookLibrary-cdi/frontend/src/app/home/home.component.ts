import {Component, OnInit} from '@angular/core';
import {AuthorService} from "../services/author.service";
import {BookService} from "../services/book.service";
import {Author} from "../model/author";
import {Book} from "../model/book";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

    readonly title: string = 'Home';
    authors: Author[] = [];
    books: Book[] = [];
    rowNumberArray: number[] = [5, 4, 3, 2, 1, 0];
    readonly authorsClassificationTitle: string[] = [
        'Be the first one',
        'Don\'t spend your time',
        'Only for fans',
        'Not bad',
        'Pay attention',
        'Guru of words'
    ];
    readonly booksClassificationTitle: string[] = [
        'Be the first one',
        'Don\'t spend your time',
        'Only for fans',
        'Not bad',
        'Must read',
        'Blow your imagination'
    ];

    constructor(private authorService: AuthorService,
                private bookService: BookService) {
    }

    ngOnInit(): void {
        this.authorService.getAuthors().subscribe(
            authors => {
                this.authors = authors;
            },
            error => {
                console.log()
            });
        this.bookService.getBooks().subscribe(
            books => {
                this.books = books;
            },
            error => {
                console.log()
            });
    }

    getCountAuthorsByAverageRating(rating: number): number {
        let ratingFrom = rating - 0.5;
        let ratingTo = rating - 0.5 + 1;
        if (rating === 0) {
            ratingFrom = 0;
            ratingTo = 0;
        }
        if (rating === 1) {
            ratingFrom = 0.01;
        }
        if (ratingFrom <= 0) {
            ratingFrom = 0;
        }
        if (ratingTo > 5) {
            ratingTo = 5;
        }
        let countArray: Author[] = this.authors.filter(a => (a.authorRating >= ratingFrom) && (a.authorRating <= ratingTo));
        return countArray.length;
    }

    getCountBooksByAverageRating(rating: number): number {
        let ratingFrom = rating - 0.5;
        let ratingTo = rating - 0.5 + 1;
        if (rating === 0) {
            ratingFrom = 0;
            ratingTo = 0;
        }
        if (rating === 1) {
            ratingFrom = 0.01;
        }
        if (ratingFrom <= 0) {
            ratingFrom = 0;
        }
        if (ratingTo > 5) {
            ratingTo = 5;
        }
        let countArray: Book[] = this.books.filter(b => (b.bookRating >= ratingFrom) && (b.bookRating <= ratingTo));
        return countArray.length;
    }
}
