import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {BookFilter} from "../../model/book-filter";
import {AuthorFilter} from "../../model/author-filter";
import {RequestOptions} from "../../model/request-options";

@Component({
    selector: 'app-books-filtering',
    templateUrl: './books-filtering.component.html',
    styleUrls: ['./books-filtering.component.scss']
})
export class BooksFilteringComponent implements OnInit {

    @Input() requestOptions: RequestOptions<BookFilter>;
    @Output() initFilteredBook: EventEmitter<RequestOptions<BookFilter>> = new EventEmitter<RequestOptions<BookFilter>>();
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

    searchFilteredBooks(requestOptions: RequestOptions<BookFilter>): void {
        this.requestOptions.pageNumber = 0;
        this.initFilteredBook.emit(requestOptions);
    }

    reset(): void {
        this.requestOptions.filteredEntity = new BookFilter(null, null, null, null, null, null);
    }

    getCurrentYear(): number {
        return new Date().getFullYear();
    }
}
