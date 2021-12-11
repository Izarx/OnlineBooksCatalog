import {Component, OnInit} from '@angular/core';
import {ResponseData} from "../../model/response-data";
import {PaginationService} from "../../services/pagination.service";
import {Book} from "../../model/book";
import {BookService} from "../../services/book.service";
import {SortableColumn} from "../../model/sortable-column";
import {SortingService} from "../../services/sorting.service";
import {RequestOptions} from "../../model/request-options";
import {BookFilter} from "../../model/book-filter";
import {AuthorFilter} from "../../model/author-filter";

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

    page: ResponseData<Book>;
    book: Book = new Book(null, '', 0, '', '', 0.00, []);
    requestOptions: RequestOptions<BookFilter> = new RequestOptions();
    deniedToDeleteBooks: Book[] = [];
    isAllChecked: boolean;

    constructor(private bookService: BookService,
                private paginationService: PaginationService<BookFilter>,
                private sortingService: SortingService) {
        this.requestOptions.filteredEntity = new BookFilter(null, new AuthorFilter(null, null, null), null, null, null, null);
        this.isAllChecked = false;
    }

    ngOnInit(): void {
        this.page = new ResponseData<Book>();
        this.getData();
    }

    getData(): void {
        this.bookService.getPage(this.requestOptions).subscribe(
            page => {
                this.paginationService.initPageable(this.requestOptions, page.totalElements);
                this.page = page;
                this.isAllChecked = false;
            },
            error => {
                console.log(error);
            });
    }

    getBookById(bookId: number): void {
        this.bookService.getBookById(bookId).subscribe(
            book => {
                this.book = book;
            },
            error => {
                console.log(error);
            });
    }

    setBookByIdWithAuthors(bookId: number): void {
        this.bookService.getBookByIdWithAuthors(bookId).subscribe(
            book => {
                this.book = book;
            },
            error => {
                console.log(error);
            });
    }

    deleteBook(bookId: number): void {
        this.bookService.deleteBook(bookId).subscribe(
            () => {
                this.getData();
            },
            error => {
                console.log(error);
            });
    }

    bulkDeleteBooks(): void {
        this.bookService.bulkDeleteBooks(this.setBooksForDelete().map(a => a.bookId)).subscribe(
            books => {
                this.deniedToDeleteBooks = books;
                this.getData();
            },
            error => {
                console.log(error);
            });
    }

    public sort(sortableColumn: SortableColumn): void {
        this.requestOptions.sorting = this.sortingService.changeSortableStateColumn(sortableColumn, this.requestOptions.sorting);
        this.getData();
    }

    public getNextPage(): void {
        this.requestOptions = this.paginationService.getNextPage(this.requestOptions);
        this.getData();
    }

    public getPreviousPage(): void {
        this.requestOptions = this.paginationService.getPreviousPage(this.requestOptions);
        this.getData();
    }

    public getPageInNewSize(pageSize: number): void {
        this.requestOptions = this.paginationService.getPageInNewSize(this.requestOptions, pageSize);
        this.getData();
    }

    public getPageNewNumber(pageNumber: number): void {
        this.requestOptions = this.paginationService.getPageNewNumber(this.requestOptions, pageNumber);
        this.getData();
    }

    public getFilteredData(requestOptions: RequestOptions<BookFilter>): void {
        this.requestOptions = requestOptions;
        this.getData();
    }

    setCheckForAll(): void {
        this.page.content.forEach(a => a.isChecked = this.isAllChecked);
    }

    setBooksForDelete(): Book[] {
        return this.page.content.filter(a => a.isChecked);
    }

    setBook(book: Book): void {
        this.book = book;
    }

    isbnOutput(isbn: string): string {
        return isbn.substr(0, 3) + '-' + isbn.substr(3, 9) + '-' + isbn.substr(12, 1);
    }
}
