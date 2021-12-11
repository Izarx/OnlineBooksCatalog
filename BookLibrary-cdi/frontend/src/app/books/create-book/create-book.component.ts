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

    dropdownList: Author[] = [];
    selectedItems: Author[] = [];
    dropdownSettings: IDropdownSettings = {};

    requestOptions: RequestOptions<AuthorFilter>;
    form: FormGroup = new FormGroup({});
    book: Book = new Book(null, null, null, null, '', 0.00, []);
    @Output() initParentPage: EventEmitter<any> = new EventEmitter<any>();

    constructor(private bookService: BookService,
                private authorService: AuthorService) {
    }

    ngOnInit(): void {
        this.requestOptions = new RequestOptions<AuthorFilter>();
        this.requestOptions.filteredEntity = new AuthorFilter(null, null, null);
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
                console.log(book);
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
            this.book.isbn = formData.isbn;
            this.book.publisher = formData.publisher.trim();
            this.book.authors = this.selectedItems;
            this.createBook(this.book);
            document.getElementById('createBookModalCloseButton').click();
        }
    }

    cancel(): void {
        if (this.form.valid) {
            this.form.reset();
        }
        this.selectedItems = [];
        this.dropdownList = [];
    }

    onItemSelect(author: any): void {
        this.onItemDeselect(author);
        this.selectedItems.push(this.dropdownList.find(a => a.authorId === author.authorId));
    }

    onSelectAll(authors: any): void {
        authors.forEach(i => this.onItemDeselect(i))
            .forEach(i => this.selectedItems.push(this.dropdownList.find(a => a.authorId === i.authorId)));
    }

    onFilterChange(filterString: any): void {
        this.requestOptions.filteredEntity.name = filterString;
        this.getData();
    }

    onItemDeselect(author: any): void {
        this.selectedItems = this.selectedItems.filter(a => a.authorId !== author.authorId);
    }

    onDeselectAll(authors: any): void {
        this.selectedItems = authors;
    }
}
