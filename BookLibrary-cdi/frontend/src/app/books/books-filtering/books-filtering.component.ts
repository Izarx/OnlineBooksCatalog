import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {BookFilter} from "../../model/book-filter";
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
            bookRatingFrom: new FormControl(null, []),
            bookRatingTo: new FormControl(null, []),
            yearPublished: new FormControl(null, []),
            isbn: new FormControl(null, []),
        });
    }

    searchFilteredBooks(requestOptions: RequestOptions<BookFilter>): void {
        if (this.bookFilteringForm.valid) {
            const formData = {...this.bookFilteringForm.value};
            this.requestOptions.pageNumber = 0;
            this.requestOptions.filteredEntity.ratingFrom = formData.bookRatingFrom;
            this.requestOptions.filteredEntity.ratingTo = formData.bookRatingTo;
            let isbn: string = this.requestOptions.filteredEntity.isbn;
            if (isbn !== null && isbn !== undefined) {
                let re = /-/gi;
                this.requestOptions.filteredEntity.isbn = isbn.replace(re, '').trim();
            }
            this.initFilteredBook.emit(requestOptions);
        }
    }

    reset(): void {
        this.bookFilteringForm.reset();
        this.requestOptions.filteredEntity = new BookFilter(null, null, null, null, null, null);
    }

    getCurrentYear(): number {
        return new Date().getFullYear();
    }

    setRatingTo() {
        const formData = {...this.bookFilteringForm.value};
        this.requestOptions.filteredEntity.ratingTo = formData.bookRatingTo;
    }

    setRatingFrom() {
        const formData = {...this.bookFilteringForm.value};
        this.requestOptions.filteredEntity.ratingFrom = formData.bookRatingFrom;
    }
}
