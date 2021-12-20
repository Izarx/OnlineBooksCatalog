import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Book} from "../../model/book";
import {BookService} from "../../services/book.service";
import {IDropdownSettings} from "ng-multiselect-dropdown";
import {RequestOptions} from "../../model/request-options";
import {AuthorFilter} from "../../model/author-filter";
import {AuthorService} from "../../services/author.service";
import {Author} from "../../model/author";

@Component({
    selector: 'app-update-book',
    templateUrl: './update-book.component.html',
    styleUrls: ['./update-book.component.scss']
})
export class UpdateBookComponent implements OnInit {

    dropdownSettings: IDropdownSettings = {};
    requestOptions: RequestOptions<AuthorFilter>;
    dropdownList: any[] = [];
    selectedAuthors: Author[] = [];
    form: FormGroup = new FormGroup({});

    @Input() selectedItems: any[];
    @Input() book: Book;
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
            publisher: new FormControl('', []),
            authors: new FormControl('', Validators.required)
        });
        this.dropdownSettings = {
            singleSelection: false,
            enableCheckAll: false,
            idField: 'authorId',
            textField: 'fullName',
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
            let bookName = formData.name;
            if (bookName !== null && bookName !== undefined) {
                this.book.name = bookName.trim();
            }
            this.book.yearPublished = formData.yearPublished;
            this.book.isbn = formData.isbn;
            let publisher = formData.publisher;
            if (publisher !== null && publisher !== undefined) {
                this.book.publisher = publisher.trim();
            }
            this.book.authors.forEach(a => this.selectedAuthors.push(a));
            this.selectedAuthors = this.selectedAuthors.filter(a => this.selectedItems.find(i => a.authorId === i.authorId) != null);
            this.book.authors = this.selectedAuthors;
            this.updateBook(this.book);
            this.form.reset();
            document.getElementById('updateBookModalCloseButton').click();
        }
    }

    cancel(): void {
        this.form.reset();
        this.selectedItems = this.book.authors;
        this.selectedAuthors = this.book.authors;
        this.requestOptions.filteredEntity = new AuthorFilter(null, null, null);
        this.book = new Book(null, null, null, null, null, 0.00, this.selectedItems);
        this.getData();
    }

    onItemSelect(author: any): void {
        this.selectedAuthors.push(this.dropdownList.find(a => a.authorId === author.authorId));
    }

    onFilterChange(filterString: any): void {
        this.requestOptions.filteredEntity = new AuthorFilter(filterString, null, null);
        this.getData();
    }

}
