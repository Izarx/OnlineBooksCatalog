import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Book} from "../../model/book";
import {BookService} from "../../services/book.service";
import {IDropdownSettings} from "ng-multiselect-dropdown";
import {RequestOptions} from "../../model/request-options";
import {AuthorFilter} from "../../model/author-filter";
import {AuthorService} from "../../services/author.service";

@Component({
    selector: 'app-update-book',
    templateUrl: './update-book.component.html',
    styleUrls: ['./update-book.component.scss']
})
export class UpdateBookComponent implements OnInit {

    dropdownSettings: IDropdownSettings = {};
    requestOptions: RequestOptions<AuthorFilter>;
    dropdownList: any[] = [];
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
            this.book.name = this.book.name.trim();
            this.book.yearPublished = formData.yearPublished;
            this.book.isbn = formData.isbn;
            this.book.publisher = this.book.publisher.trim();
            this.book.authors = [];
            this.selectedItems.forEach(a => this.book.authors.push(a));
            console.log('Update method *************', this.book);
            this.updateBook(this.book);
            this.form.reset();
            document.getElementById('updateBookModalCloseButton').click();
        }
    }

    cancel(): void {
        this.form.reset();
        this.selectedItems = [];
        this.book.authors.forEach(a => this.selectedItems.push(a));
        this.book = new Book(null, null, null, null, null, 0.00, this.selectedItems);
        this.getData();
    }

    onItemSelect(author: any): void {
        this.onItemDeselect(author);
        this.selectedItems.push(this.dropdownList.find(a => a.authorId === author.authorId));
    }

    onSelectAll(authors: any): void {
        authors.forEach(i => this.selectedItems.push(this.dropdownList.find(a => a.authorId === i.authorId)));
    }

    onFilterChange(filterString: any): void {
        this.requestOptions.filteredEntity = new AuthorFilter(filterString, null, null);
        this.getData();
    }

    onItemDeselect(author: any): void {
        this.selectedItems = this.selectedItems.filter(a => a.authorId !== author.authorId);
    }

    onDeselectAll(authors: any): void {
        this.selectedItems = authors;
    }

}
