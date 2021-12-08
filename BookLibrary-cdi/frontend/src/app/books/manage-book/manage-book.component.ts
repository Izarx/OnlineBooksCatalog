import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {BookService} from "../book.service";
import {Book} from "../../model/book";
import {ReviewService} from "../../reviews/review.service";
import {RequestOptions} from "../../model/request-options";
import {ReviewFilter} from "../../model/review-Filter";
import {ResponseData} from "../../model/response-data";
import {Review} from "../../model/review";

@Component({
  selector: 'app-manage-book',
  templateUrl: './manage-book.component.html',
  styleUrls: ['./manage-book.component.scss']
})
export class ManageBookComponent implements OnInit {

  book: Book = new Book(null, '', 0, '', '', 0.00, []);
  ratingStarsArray: Array<number>;
  requestOptions: RequestOptions<ReviewFilter> = new RequestOptions();
  reviews: ResponseData<Review> = new ResponseData<Review>();
  votes: number;

  constructor(
      private route: ActivatedRoute,
      private bookService: BookService,
      private reviewService: ReviewService
  ) {
    this.requestOptions.filteredEntity = new ReviewFilter(null);
  }

  ngOnInit(): void {
    this.tabBookInfoButtonVisible();
    this.getData();
  }



  tabBookInfoButtonVisible() {
    document.getElementById("bookInfoButton").hidden = false;
    document.getElementById("addCommentButton").hidden = true;
  }

  tabCommentButtonVisible() {
    document.getElementById("bookInfoButton").hidden = true;
    document.getElementById("addCommentButton").hidden = false;
  }

  initStarsRating(rating: number): Array<number> {
    let r = rating;
    this.ratingStarsArray = new Array<number>();
    while (this.ratingStarsArray.length < 5) {
      if (r >= 1) {
        this.ratingStarsArray.push(1);
      }
      else if (r < 1 && r >=0) {
        this.ratingStarsArray.push(r);
      }
      else {
        this.ratingStarsArray.push(0);
      }
      r--;
    }
    return this.ratingStarsArray;
  }

  getData() {
    this.route.params.subscribe((params: Params) => {
      let bookId = params.bookId;
      this.requestOptions.filteredEntity.bookId = bookId;
      this.bookService.getBookByIdWithAuthors(bookId).subscribe(
          book => {
            this.book = book;
          },
          error => {
            console.log(error);
          }
      );
      this.reviewService.getPage(this.requestOptions).subscribe(
          page => {
            this.reviews = page;
          },
          error => {
            console.log(error);
          }
      )
    })
  }

  getBookByIdWithAuthors(bookId: number) {
    this.bookService.getBookByIdWithAuthors(bookId).subscribe(
        book => this.book = book
    )
  }
}
