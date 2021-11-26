import {Component, OnInit, Output} from '@angular/core';
import {Author} from "../../model/author";
import {ResponseData} from "../../model/response-data";
import {AuthorService} from "../author.service";
import {PaginationService} from "../../pagination/pagination.service";
import {SortableColumn} from "../../model/sortable-column";
import {SortingService} from "../../sorting/sorting.service";
import {RequestOptions} from "../../model/request-options";

@Component({
    selector: 'app-authors-pagination-table',
    templateUrl: './authors-pagination-table.component.html',
    styleUrls: ['./authors-pagination-table.component.scss']
})
export class AuthorsPaginationTableComponent implements OnInit {

    static readonly title: string = 'Authors';

    sortableColumns: Array<SortableColumn> = [
        new SortableColumn('firstName', 'First Name', null),
        new SortableColumn('lastName', 'Last Name', null),
        new SortableColumn('authorRating', 'Rating', null),
    ];

    page: ResponseData<Author> = new ResponseData();
    requestOptions: RequestOptions = new RequestOptions();
    author: Author = new Author(null, '', '', 0.0);
    deniedToDeletionAuthors: Author[] = []
    isAllChecked: boolean;

    constructor(
        private authorService: AuthorService,
        private paginationService: PaginationService,
        private sortingService: SortingService
    ) {
        this.isAllChecked  = false;
    }

    ngOnInit(): void {
        this.getData()
    }

    getData(): void {
        this.authorService.getPage(this.requestOptions)
            .subscribe(page => {
                    this.paginationService.initPageable(this.requestOptions, page.totalElements);
                    this.page = page;
                    this.isAllChecked  = false;
                },
                error => {
                    console.log(error)
                });
    }

    deleteAuthor(authorId: number): void {
        this.authorService.deleteAuthor(authorId).subscribe(
            () => {
                this.getData()
            },
            error => {
                console.log(error)
            })
    }

    bulkDeleteAuthors() {
        this.authorService.bulkDeleteAuthors(this.setAuthorsForDelete().map(a => a.authorId)).subscribe(
            authors => {
                this.deniedToDeletionAuthors = authors
                this.getData()
            },
            error => {
                console.log(error)
            }
        )
    }

    getAuthorById(authorId: number) {
        this.authorService.getAuthorById(authorId).subscribe(
            author => this.author = author
        )
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

    setCheckForAll() {
        this.page.content.forEach(a => a.isChecked = this.isAllChecked);
    }

    setAuthorsForDelete(): Author[] {
        return this.page.content.filter(a => a.isChecked);
    }

}
