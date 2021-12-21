import {Component, OnInit} from '@angular/core';
import {Author} from "../../model/author";
import {ResponseData} from "../../model/response-data";
import {AuthorService} from "../../services/author.service";
import {PaginationService} from "../../services/pagination.service";
import {SortableColumn} from "../../model/sortable-column";
import {SortingService} from "../../services/sorting.service";
import {RequestOptions} from "../../model/request-options";
import {AuthorFilter} from '../../model/author-filter';
import {ActivatedRoute, Params} from "@angular/router";

@Component({
    selector: 'app-authors-pagination-table',
    templateUrl: './authors-pagination-table.component.html',
    styleUrls: ['./authors-pagination-table.component.scss']
})
export class AuthorsPaginationTableComponent implements OnInit {

    readonly title: string = 'Authors';
    sortableColumns: Array<SortableColumn> = [  // todo: is this array really needed?
        new SortableColumn('firstName', 'First Name', null),
        new SortableColumn('lastName', 'Last Name', null),
        new SortableColumn('authorRating', 'Rating', null),
    ];

    page: ResponseData<Author> = new ResponseData();
    requestOptions: RequestOptions<AuthorFilter> = new RequestOptions();
    author: Author = new Author(null, '', '', 0.00);
    deniedToDeleteAuthors: Author[] = [];
    isDeleted: boolean;
    isAllChecked: boolean;

    constructor(private route: ActivatedRoute,
                private authorService: AuthorService,
                private paginationService: PaginationService<AuthorFilter>,
                private sortingService: SortingService) {
        this.requestOptions.filteredEntity = new AuthorFilter(null, null, null);
        this.isAllChecked = false;
    }

    ngOnInit(): void {
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
        this.authorService.getPage(this.requestOptions).subscribe(
            page => {
                this.paginationService.initPageable(this.requestOptions, page.totalElements);
                this.page = page;
                this.isAllChecked = false;
            },
            error => {
                console.log(error);
            });
    }

    deleteAuthor(authorId: number): void {
        this.authorService.deleteAuthor(authorId).subscribe(
            status => {
                this.isDeleted = status;
                this.getData();
            },
            error => {
                console.log(error);
            });
    }

    bulkDeleteAuthors(): void {
        this.authorService.bulkDeleteAuthors(this.setAuthorsForDelete().map(a => a.authorId)).subscribe(
            authors => {
                this.deniedToDeleteAuthors = authors;
                this.requestOptions = new RequestOptions();
                this.requestOptions.filteredEntity = new AuthorFilter(null, null, null);
                this.getData();
            },
            error => {
                console.log(error);
            });
    }

    getAuthorById(authorId: number): void {
        this.authorService.getAuthorById(authorId).subscribe(
            author => {
                this.author = author;
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

    public setFilteredData(requestOptions: RequestOptions<AuthorFilter>): void {
        this.requestOptions = requestOptions;
        this.getData();
    }

    setCheckForAll(): void {
        this.page.content.forEach(a => a.isChecked = this.isAllChecked);
    }

    setAuthorsForDelete(): Author[] {
        return this.page.content.filter(a => a.isChecked);
    }

    setAuthor(author: Author): void {
        this.author = author;
    }

}
