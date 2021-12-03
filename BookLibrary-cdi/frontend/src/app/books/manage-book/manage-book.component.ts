import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {BookService} from "../book.service";
import {Book} from "../../model/book";

@Component({
  selector: 'app-manage-book',
  templateUrl: './manage-book.component.html',
  styleUrls: ['./manage-book.component.scss']
})
export class ManageBookComponent implements OnInit {

  book: Book = new Book(null, '', 0, 0, '', 0, [])

  constructor(
      private route: ActivatedRoute,
      private bookService: BookService
  ) { }

  ngOnInit(): void {
    this.route.params.subscribe((params: Params) => {
      this.bookService.getBookByIdWithAuthors(params.bookId).subscribe(
          book => {
            this.book = book;
          }
      )
    })
  }

}
