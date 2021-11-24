import {Component, OnInit} from '@angular/core';
import {Author} from "../../model/author";
import {AppResponsePage} from "../../model/app-response-page";
import {AuthorService} from "../author.service";
import {PaginationService} from "../../pagination/pagination.service";
import {SortableColumn} from "../../model/sortable-column";
import {SortingService} from "../../sorting/sorting.service";
import {AppRequestPage} from "../../model/app-request-page";

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

  page: AppResponsePage<Author> = new AppResponsePage();
  appRequestPage: AppRequestPage = new AppRequestPage();
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
      this.authorService.getPage(this.appRequestPage)
        .subscribe(page => {
            this.paginationService.initPageable(this.appRequestPage, page.totalElements);
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
      this.appRequestPage.sorting = this.sortingService.changeSortableStateColumn(sortableColumn, this.appRequestPage.sorting);
      console.log(this.appRequestPage.sorting);
      this.getData();
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

  setAuthor(author: Author) {
    this.author = author;
    // formData.firstName = this.author.firstName;
    // formData.lastName = this.author.lastName;
  }

  getAuthor() : Author {
    return this.author;
  }
}
