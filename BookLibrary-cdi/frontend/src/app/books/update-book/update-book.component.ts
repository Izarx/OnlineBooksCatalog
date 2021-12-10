import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Book} from "../../model/book";
import {BookService} from "../book.service";

@Component({
    selector: 'app-update-book',
    templateUrl: './update-book.component.html',
    styleUrls: ['./update-book.component.scss']
})
export class UpdateBookComponent implements OnInit {

    form: FormGroup;
    @Input() book: Book;
    @Output() initParentPage: EventEmitter<any> = new EventEmitter<any>();

    constructor(private bookService: BookService) {
    }

    ngOnInit(): void {
        this.form = new FormGroup({
            name: new FormControl(),
            yearPublished: new FormControl(),
            isbn: new FormControl(),
            authors: new FormControl()
        });
    }

    updateBook(book: Book): void {
        this.bookService.updateBook(book).subscribe(
            book => {
                this.book = book;
                this.initParentPage.emit(null);
            },
            error => {
                console.log(error);
            });
    }

    submit(): void {
        if (this.form.valid) {
            const formData = {...this.form.value};
            this.book.name = formData.name;
            this.book.yearPublished = formData.yearPublished;
            this.book.isbn = formData.isbn;
            this.book.authors = formData.authors;
            this.updateBook(this.book);
            this.form.reset();
            document.getElementById('updateBookModalCloseButton').click();
        }
    }

    cancel(): void {
        this.form.reset();
    }

}
