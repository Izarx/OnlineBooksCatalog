import {Component, OnInit} from '@angular/core';
import {ResponseData} from "../../model/response-data";
import {PaginationService} from "../../pagination/pagination.service";
import {Book} from "../../model/book";
import {BookService} from "../book.service";
import {Author} from "../../model/author";
import {SortableColumn} from "../../model/sortable-column";
import {SortingService} from "../../sorting/sorting.service";
import {RequestOptions} from "../../model/request-options";

@Component({
    selector: 'app-books-pagination-table',
    templateUrl: './books-pagination-table.component.html',
    styleUrls: ['./books-pagination-table.component.scss']
})
export class BooksPaginationTableComponent implements OnInit {

    sortableColumns: Array<SortableColumn> = [
        new SortableColumn('name', 'Book Name', null),
        new SortableColumn('bookRating', 'Rating', null),
        new SortableColumn('yearPublished', 'Year', null),
        new SortableColumn('isbn', 'ISBN', null),
        new SortableColumn('publisher', 'Publisher', null),
    ];

    page: ResponseData<Book> = new ResponseData()
    book: Book = new Book(null, '', 0, 0, '', 0.0, null)
    appRequestPage: RequestOptions = new RequestOptions();
    authors: Author[]

    constructor(
        private bookService: BookService,
        private paginationService: PaginationService,
        private sortingService: SortingService,
    ) {
    }

    ngOnInit(): void {
        this.getData()
    }

    public sort(sortableColumn: SortableColumn): void {
        this.appRequestPage.sorting = this.sortingService.changeSortableStateColumn(sortableColumn, this.appRequestPage.sorting);
        console.log(this.appRequestPage.sorting);
        this.getData();
    }

    deleteBook(bookId: number): void {
        this.bookService.deleteBook(bookId).subscribe(
            () => {
                this.getData()
            },
            error => {
                console.log(error)
            })
    }

    public getNextPage(): void {
        this.appRequestPage = this.paginationService.getNextPage(this.appRequestPage);
        this.getData();
    }

    public getPreviousPage(): void {
        this.appRequestPage = this.paginationService.getPreviousPage(this.appRequestPage);
        this.getData();
    }

    public getPageInNewSize(pageSize: number): void {
        this.appRequestPage = this.paginationService.getPageInNewSize(this.appRequestPage, pageSize);
        this.getData();
    }

    public getPageNewNumber(pageNumber: number): void {
        this.appRequestPage = this.paginationService.getPageNewNumber(this.appRequestPage, pageNumber);
        this.getData();
    }

    setBook(book: Book) {
        this.book = book
    }

    getBook(): Book {
        return this.book;
    }

    private getData(): void {
        this.bookService.getPage(this.appRequestPage)
            .subscribe(page => {
                    this.paginationService.initPageable(this.appRequestPage, page.totalElements);
                    this.page = page
                },
                error => {
                    console.log(error)
                });
    }
}
