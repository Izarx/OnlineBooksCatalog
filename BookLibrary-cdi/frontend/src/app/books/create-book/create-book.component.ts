import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Book} from "../../model/book";
import {BookService} from "../../services/book.service";
import {Author} from "../../model/author";
import {RequestOptions} from "../../model/request-options";
import {AuthorFilter} from "../../model/author-filter";
import {AuthorService} from "../../services/author.service";
import {IDropdownSettings} from "ng-multiselect-dropdown";

@Component({
    selector: 'app-create-book',
    templateUrl: './create-book.component.html',
    styleUrls: ['./create-book.component.scss']
})
export class CreateBookComponent implements OnInit {

    dropdownList: any[] = [];
    selectedItems: any[] = [];
    selectedAuthors: Author[] = [];
    dropdownSettings: IDropdownSettings = {};
    requestOptions: RequestOptions<AuthorFilter>;
    form: FormGroup = new FormGroup({});
    book: Book;

    @Output() initParentPage: EventEmitter<any> = new EventEmitter<any>();

    constructor(private bookService: BookService,
                private authorService: AuthorService) {
    }

    ngOnInit(): void {
        this.requestOptions = new RequestOptions<AuthorFilter>();
        this.requestOptions.filteredEntity = new AuthorFilter(null, null, null);
        this.book = new Book(null, null, null, null, null, 0.00, []);
        this.getData();
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
            textField: 'fullName',
            selectAllText: 'Select All',
            unSelectAllText: 'UnSelect All',
            itemsShowLimit: 5,
            allowSearchFilter: true,
            clearSearchFilter: true
        };
    }

    getData(): void {
        this.authorService.getPage(this.requestOptions).subscribe(
            page => {
                this.dropdownList = page.content;
                this.dropdownList.map(a => a.fullName = a.firstName + ' ' + a.lastName);
            },
            error => {
                console.log(error);
            });
    }

    createBook(book: Book): void {
        this.bookService.createBook(book).subscribe(
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
            this.book.bookId = null;
            this.book.name = formData.name.trim();
            this.book.yearPublished = formData.yearPublished;
            this.book.isbn = formData.isbn;
            this.book.publisher = formData.publisher.trim();
            this.selectedAuthors = this.selectedAuthors.filter(a => this.selectedItems.find(i => a.authorId === i.authorId) != null);
            this.book.authors = this.selectedAuthors;
            this.createBook(this.book);
            this.onFilterChange(null);
            document.getElementById('createBookModalCloseButton').click();
        }
    }

    cancel(): void {
        this.form.reset();
        this.selectedItems = [];
        this.selectedAuthors = [];
        this.requestOptions.filteredEntity = new AuthorFilter(null, null, null);
        this.book = new Book(null, null, null, null, null, 0.00, this.selectedItems);
        this.getData();
    }

    onItemSelect(author: any): void {
        this.selectedAuthors.push(this.dropdownList.find(a => a.authorId === author.authorId));
    }

    onSelectAll(authors: any): void {
        authors.forEach(a => this.selectedAuthors.push(this.dropdownList.find(i => a.authorId === i.authorId)));
    }

    onFilterChange(filterString: any): void {
        this.requestOptions.filteredEntity = new AuthorFilter(filterString, null, null);
        this.getData();
    }
}
