import { Component, OnInit } from '@angular/core';
import {Author} from "../../model/author";
import {Page} from "../../model/page";
import {AuthorService} from "../author.service";
import {PaginationService} from "../../pagination/pagination.service";
import {SortableColumn} from "../../model/sortable-column";
import {SortingService} from "../../sorting/sorting.service";

@Component({
  selector: 'app-authors-pagination-table',
  templateUrl: './authors-pagination-table.component.html',
  styleUrls: ['./authors-pagination-table.component.scss']
})
export class AuthorsPaginationTableComponent implements OnInit {

  sortableColumns: Array<SortableColumn> = [
      new SortableColumn('firstName', 'First Name', null),
      new SortableColumn('lastName', 'Last Name', null),
      new SortableColumn('authorRating', 'Rating', 'desc'),
  ];
  page: Page<Author> = new Page()
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
      this.page.pageConstructor.sorting = this.sortingService.getSortableColumns(this.sortableColumns);
      this.authorService.getPage(this.page.pageConstructor)
        .subscribe(page => {
          this.page = page
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
    this.page.pageConstructor.pageable = this.paginationService.getNextPage(this.page);
    this.getData();
  }

  public getPreviousPage(): void {
    this.page.pageConstructor.pageable = this.paginationService.getPreviousPage(this.page);
    this.getData();
  }

  public getPageInNewSize(pageSize: number): void {
    this.page.pageConstructor.pageable = this.paginationService.getPageInNewSize(this.page, pageSize);
    this.getData();
  }

  public getPageNewNumber(pageNumber: number): void {
      this.page.pageConstructor.pageable = this.paginationService.getPageNewNumber(this.page, pageNumber);
      this.getData();
  }

  setAuthor(author: Author) {
    this.author = author
  }

  getAuthor() : Author {
    return this.author;
  }
}
