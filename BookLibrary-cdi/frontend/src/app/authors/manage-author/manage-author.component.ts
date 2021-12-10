import { Component, OnInit } from '@angular/core';
import {Author} from "../../model/author";
import {RequestOptions} from "../../model/request-options";
import {Book} from "../../model/book";
import {ActivatedRoute, Params} from "@angular/router";
import {BookService} from "../../books/book.service";
import {AuthorService} from "../author.service";

@Component({
  selector: 'app-manage-author',
  templateUrl: './manage-author.component.html',
  styleUrls: ['./manage-author.component.scss']
})
export class ManageAuthorComponent implements OnInit {

  author: Author = new Author(null, null, null, 0.00);
  authorForUpdate: Author = new Author(null, null, null, 0.00);
  requestOptions: RequestOptions<Book> = new RequestOptions<Book>();
  ratingStarsArray: Array<number>;

  constructor(private route: ActivatedRoute,
              private authorService: AuthorService,
              private bookService: BookService) { }

  ngOnInit(): void {
    this.getData();
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
      let authorId = params.authorId;
      this.authorService.getAuthorById(authorId).subscribe(
          author => {
            this.author = author;
          },
          error => {
            console.log(error);
          }
      );
    })
  }

  getAuthorForUpdateWithAuthors(authorId: number) {
    this.authorService.getAuthorById(authorId).subscribe(
        author => this.authorForUpdate = author
    )
  }

}
