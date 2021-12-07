import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Book} from "../../model/book";
import {BookService} from "../book.service";

@Component({
    selector: 'app-create-book',
    templateUrl: './create-book.component.html',
    styleUrls: ['./create-book.component.scss']
})
export class CreateBookComponent implements OnInit {

    form: FormGroup = new FormGroup({})
    book: Book = new Book(null, null, null, null, '', 0.00, []);
    @Output() initParentPage: EventEmitter<any> = new EventEmitter<any>()

    constructor(
        private bookService: BookService
    ) {
    }

    ngOnInit(): void {
        this.form = new FormGroup({
            name: new FormControl(),
            yearPublished: new FormControl('',
                Validators.max(new Date().getFullYear())),
            isbn: new FormControl('',
                Validators.pattern("^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$"))
        })
    }

    createBook(book: Book) {
        this.bookService.createBook(book).subscribe(
            book => {
                this.initParentPage.emit(null);
            },
            error => {
                console.log(error)
            }
        );
    }

    submit() {
        if (this.form.valid) {
            const formData = {...this.form.value};
            this.book.name = formData.name;
            this.book.yearPublished = formData.yearPublished;
            this.book.isbn = formData.isbn.trim();
            this.book.authors = formData.authors;
            this.createBook(this.book);
            document.getElementById('createBookModalCloseButton').click()
        }
    }

    cancel() {
        if (this.form.valid) {
            this.form.reset();
        }
    }
}
