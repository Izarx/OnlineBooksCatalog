import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Book} from "../../model/book";
import {AuthorService} from "../../authors/author.service";
import {Author} from "../../model/author";
import {RequestOptions} from "../../model/request-options";

@Component({
  selector: 'app-books-filtering',
  templateUrl: './books-filtering.component.html',
  styleUrls: ['./books-filtering.component.scss']
})
export class BooksFilteringComponent implements OnInit {

  @Input() filteredBook: Book;
  @Output() initFilteredBook: EventEmitter<Book> = new EventEmitter<Book>()
  bookFilteringForm: FormGroup = new FormGroup({});
  authorFilteringString: string = '';
  authors: Array<Author>;

  constructor(
      private authorService: AuthorService
  ) { }

  ngOnInit(): void {
    this.bookFilteringForm = new FormGroup({
      name: new FormControl('', []),
      authors: new FormControl('', []),
      bookRating: new FormControl(0.00, [
        Validators.min(0.00),
        Validators.max(5.00),
      ]),
      bookRatingRange: new FormControl(null, [
        Validators.min(this.filteredBook.bookRating),
        Validators.max(5.00)
      ]),
      yearPublished: new FormControl(0, [
          Validators.min(0),
          Validators.max(this.getCurrentYear())
      ]),
      yearPublishedRange: new FormControl(null, [
          Validators.min(this.filteredBook.bookRating),
          Validators.max(this.getCurrentYear())
      ]),
      isbn: new FormControl('', []),
    });
  }

  searchFilteredBooks(filteredBook: Book) {
    if (filteredBook.bookRating === null) {
      filteredBook.bookRating = 0.00;
    }
    if (filteredBook.yearPublished === null) {
      filteredBook.yearPublished = 0;
    }
    this.initFilteredBook.emit(filteredBook);
  }

  reset() {
    this.filteredBook.name = null;
    this.filteredBook.authors = [];
    this.filteredBook.bookRating = 0.00;
    this.filteredBook.bookRatingRange = null;
    this.filteredBook.yearPublished = 0;
    this.filteredBook.yearPublishedRange = null;
    this.filteredBook.isbn = null;
    this.authorFilteringString = null
  }

  getCurrentYear() {
    return new Date().getFullYear();
  }

  getAllAuthors() : Array<Author> {
    let options = new RequestOptions<Author>();
    options.filteredEntity.firstName = this.authorFilteringString;
    this.authorService.getPage(options).subscribe(
        authors => {
          this.authors = authors.content;
        },
        error => {
          console.log(error);
        }
    )
    return this.authors;
  }

}
