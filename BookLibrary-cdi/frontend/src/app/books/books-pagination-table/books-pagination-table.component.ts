import {Component, OnInit} from '@angular/core';
import {ResponseData} from "../../model/response-data";
import {PaginationService} from "../../services/pagination.service";
import {Book} from "../../model/book";
import {BookService} from "../../services/book.service";
import {SortableColumn} from "../../model/sortable-column";
import {SortingService} from "../../services/sorting.service";
import {RequestOptions} from "../../model/request-options";
import {BookFilter} from "../../model/book-filter";
import {AuthorService} from "../../services/author.service";
import {ActivatedRoute, Params} from "@angular/router";

@Component({
    selector: 'app-books-pagination-table',
    templateUrl: './books-pagination-table.component.html',
    styleUrls: ['./books-pagination-table.component.scss']
})
export class BooksPaginationTableComponent implements OnInit {

    readonly title: string = 'Books';
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

    constructor(private route: ActivatedRoute,
                private bookService: BookService,
                private authorService: AuthorService,
                private paginationService: PaginationService<BookFilter>,
                private sortingService: SortingService) {
        this.requestOptions.filteredEntity = new BookFilter(null, null, null, null, null, null);
        this.isAllChecked = false;
    }

    ngOnInit(): void {
        this.page = new ResponseData<Book>();
        this.getData();
    }

    getData(): void {
        this.route.params.subscribe((params: Params) => {
            let rating = params.rating;
            let ratingFrom = null;
            let ratingTo = null;
            if (rating !== null && rating !== undefined && this.requestOptions.filteredEntity.ratingFrom === null && this.requestOptions.filteredEntity.ratingTo === null) {
                ratingFrom = rating - 0.5;
                ratingTo = rating - 0.5 + 1;
                if (ratingFrom < 1) {
                    ratingFrom = 0;
                }
                if (ratingTo > 5) {
                    ratingTo = 5;
                }
                this.requestOptions.filteredEntity.ratingFrom = ratingFrom;
                this.requestOptions.filteredEntity.ratingTo = ratingTo;
            }
            },
            error => {
                console.log(error);
            });

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
                this.book.authors.map(a => a.fullName = a.firstName + ' ' + a.lastName);
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
                this.requestOptions = new RequestOptions();
                this.requestOptions.filteredEntity = new BookFilter(null, null, null, null, null, null);
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
