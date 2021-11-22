import { Component, OnInit } from '@angular/core';
import {Author} from "../../model/author";
import {Page} from "../../model/page";
import {AuthorService} from "../author.service";
import {PaginationService} from "../../pagination/pagination.service";
import {SortableColumn} from "../../model/sortable-column";
import {SortingService} from "../../sorting/sorting.service";
import {PageConstructor} from "../../model/page-constructor";

@Component({
  selector: 'app-authors-pagination-table',
  templateUrl: './authors-pagination-table.component.html',
  styleUrls: ['./authors-pagination-table.component.scss']
})
export class AuthorsPaginationTableComponent implements OnInit {

  sortableColumns: Array<SortableColumn> = [
      new SortableColumn('firstName', 'First Name', null),
      new SortableColumn('lastName', 'Last Name', null),
      new SortableColumn('authorRating', 'Rating', null),
  ];
  sortedColumns: Array<SortableColumn> = [
      new SortableColumn('authorRating', 'Rating', 'desc')
  ];
  page: Page<Author> = new Page();
  pageConstructor: PageConstructor = new PageConstructor();
  author: Author = new Author(null, '', '', 0.0)

  constructor(
      private authorService: AuthorService,
      private paginationService: PaginationService,
      private sortingService: SortingService
  ) { }

  ngOnInit(): void {
    this.getData()
  }

  private getData(): void {
      this.pageConstructor.sorting = this.sortingService.getSortableColumns(this.sortableColumns);
      this.authorService.getPage(this.pageConstructor)
        .subscribe(page => {
            this.paginationService.initPageable(this.pageConstructor.pageable, page.totalElements);
            this.page = page;
        },
            error => {
              console.log(error)
            });
  }

  deleteAuthor(authorId : number) : void {
    this.authorService.deleteAuthor(authorId).subscribe(
        () => {
          this.getData()
        },
        error => {
          console.log(error)
        })
  }

  public sort(sortableColumn: SortableColumn): void {
      this.sortingService.changeSortableStateColumn(sortableColumn, this.sortableColumns);
      this.getData();
  }

  public getNextPage(): void {
    this.pageConstructor.pageable = this.paginationService.getNextPage(this.pageConstructor.pageable);
    this.getData();
  }

  public getPreviousPage(): void {
    this.pageConstructor.pageable = this.paginationService.getPreviousPage(this.pageConstructor.pageable);
    this.getData();
  }

  public getPageInNewSize(pageSize: number): void {
    this.pageConstructor.pageable = this.paginationService.getPageInNewSize(this.pageConstructor.pageable, pageSize);
    this.getData();
  }

  public getPageNewNumber(pageNumber: number): void {
      this.pageConstructor.pageable = this.paginationService.getPageNewNumber(this.pageConstructor.pageable, pageNumber);
      this.getData();
  }

  setAuthor(author: Author) {
    this.author = author
  }

  getAuthor() : Author {
    return this.author;
  }
}
