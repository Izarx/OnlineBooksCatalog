import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Book} from "../../model/book";
import {BookService} from "../book.service";
import {Author} from "../../model/author";
import {RequestOptions} from "../../model/request-options";
import {AuthorFilter} from "../../model/author-filter";
import {AuthorService} from "../../authors/author.service";
import {IDropdownSettings} from "ng-multiselect-dropdown";

@Component({
    selector: 'app-create-book',
    templateUrl: './create-book.component.html',
    styleUrls: ['./create-book.component.scss']
})
export class CreateBookComponent implements OnInit {

    dropdownList = [];
    selectedItems = [];
    dropdownSettings: IDropdownSettings = {};

    requestOptions: RequestOptions<AuthorFilter> = new RequestOptions<AuthorFilter>();
    form: FormGroup = new FormGroup({});
    book: Book = new Book(null, null, null, null, '', 0.00, []);
    @Output() initParentPage: EventEmitter<any> = new EventEmitter<any>();

    constructor(private bookService: BookService,
                private authorService: AuthorService) {
    }

    ngOnInit(): void {
        this.requestOptions.pageSize = 5;
        this.requestOptions.filteredEntity = new AuthorFilter(null, null, null);
        this.authorService.getPage(this.requestOptions).subscribe(
            page => {
                this.dropdownList = page.content;
            },
            error => {
                console.log(error);
            });
        this.form = new FormGroup({
            name: new FormControl(),
            yearPublished: new FormControl('',
                Validators.max(new Date().getFullYear())),
            isbn: new FormControl('',
                Validators.pattern("^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$")),
            publisher: new FormControl('', [])
        });
        this.dropdownSettings = {
            singleSelection: false,
            idField: 'authorId',
            textField: 'firstName',
            selectAllText: 'Select All',
            unSelectAllText: 'UnSelect All',
            itemsShowLimit: 5,
            allowSearchFilter: true
        };
    }

    createBook(book: Book): void {
        this.bookService.createBook(book).subscribe(
            book => {
                this.initParentPage.emit(null);
            },
            error => {
                console.log(error);
            });
    }

    submit(): void {
        if (this.form.valid) {
            const formData = {...this.form.value};
            this.book.name = formData.name.trim();
            this.book.yearPublished = formData.yearPublished;
            this.book.isbn = formData.isbn.trim();
            this.book.authors = formData.authors;
            this.book.publisher = formData.publisher.trim();
            this.createBook(this.book);
            document.getElementById('createBookModalCloseButton').click();
        }
    }

    cancel(): void {
        if (this.form.valid) {
            this.form.reset();
        }
    }

    setAuthors(authors: Array<Author>): void {
        this.book.authors = authors;
    }

    onItemSelect(item: any): void {
        console.log(item);
    }

    onSelectAll(items: any): void {
        console.log(items);
    }
}
