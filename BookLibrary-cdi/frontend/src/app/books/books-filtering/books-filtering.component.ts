import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {BookFilter} from "../../model/book-filter";
import {AuthorFilter} from "../../model/author-filter";

@Component({
    selector: 'app-books-filtering',
    templateUrl: './books-filtering.component.html',
    styleUrls: ['./books-filtering.component.scss']
})
export class BooksFilteringComponent implements OnInit {

    @Input() bookFilter: BookFilter;
    @Output() initFilteredBook: EventEmitter<BookFilter> = new EventEmitter<BookFilter>();
    bookFilteringForm: FormGroup = new FormGroup({});

    constructor() {
    }

    ngOnInit(): void {
        this.bookFilteringForm = new FormGroup({
            name: new FormControl(null, []),
            authors: new FormControl(null, []),
            bookRating: new FormControl(null, []),
            bookRatingRange: new FormControl(null, []),
            yearPublished: new FormControl(null, []),
            yearPublishedRange: new FormControl(null, []),
            isbn: new FormControl(null, []),
        });
    }

    searchFilteredBooks(bookFilter: BookFilter): void {
        console.log(bookFilter);
        this.initFilteredBook.emit(bookFilter);
    }

    reset(): void {
        this.bookFilter.name = null;
        this.bookFilter.authorFilter = new AuthorFilter(null, null, null);
        this.bookFilter.bookRatingFrom = null;
        this.bookFilter.bookRatingTo = null;
        this.bookFilter.year = null;
        this.bookFilter.isbn = null;
    }

    getCurrentYear(): number {
        return new Date().getFullYear();
    }
}
