import {Component, OnInit} from '@angular/core';
import {Page} from "../../model/page";
import {PaginationService} from "../../pagination/pagination.service";
import {Book} from "../../model/book";
import {BookService} from "../book.service";
import {Author} from "../../model/author";
import {SortableColumn} from "../../model/sortable-column";
import {SortingService} from "../../sorting/sorting.service";
import {PageConstructor} from "../../model/page-constructor";

@Component({
  selector: 'app-books-pagination-table',
  templateUrl: './books-pagination-table.component.html',
  styleUrls: ['./books-pagination-table.component.scss']
})
export class BooksPaginationTableComponent implements OnInit {

  sortableColumns: Array<SortableColumn> = [
    new SortableColumn('name', 'Book Name', null),
    new SortableColumn('bookRating', 'Rating', 'desc'),
    new SortableColumn('yearPublished', 'Year', null),
    new SortableColumn('isbn', 'ISBN', null),
    new SortableColumn('publisher', 'Publisher', null),
  ];

  page: Page<Book> = new Page()
  book: Book = new Book(null, '', 0, 0, '', 0.0, null)
  pageConstructor: PageConstructor = new PageConstructor();
  authors: Author[]

  constructor(
      private bookService: BookService,
      private paginationService: PaginationService,
      private sortingService: SortingService,
  ) { }

  ngOnInit(): void {
    this.getData()
  }

  private getData(): void {
    this.pageConstructor.sorting = this.sortingService.getSortableColumns(this.sortableColumns);
    this.bookService.getPage(this.pageConstructor)
        .subscribe(page => {
              this.paginationService.initPageable(this.pageConstructor.pageable, page.totalElements);
              this.page = page
            },
            error => {
              console.log(error)
            });
  }

  public sort(sortableColumn: SortableColumn): void {
    this.sortingService.changeSortableStateColumn(sortableColumn, this.sortableColumns);
    this.getData();
  }

  deleteBook(bookId : number) : void {
    this.bookService.deleteBook(bookId).subscribe(
        () => {
          this.getData()
        },
        error => {
          console.log(error)
        })
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

  setBook(book: Book) {
    this.book = book
  }

  getBook() : Book {
    return this.book;
  }
}
