import {Component, OnInit} from '@angular/core';
import {Page} from "../../model/page";
import {PaginationService} from "../../pagination/pagination.service";
import {Book} from "../../model/book";
import {BookService} from "../book.service";
import {Author} from "../../model/author";
import {SortableColumn} from "../../model/sortable-column";
import {SortingService} from "../../sorting/sorting.service";

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
  book: Book = new Book(null, '', 0, 0, '', 0.0)
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
    this.page.pageConstructor.sorting = this.sortingService.getSortableColumns(this.sortableColumns);
    this.bookService.getPage(this.page.pageConstructor)
        .subscribe(page => {
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

  setBook(book: Book) {
    this.book = book
  }

  getBook() : Book {
    return this.book;
  }
}
