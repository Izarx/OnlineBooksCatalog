import { Component, OnInit } from '@angular/core';
import {Author} from "../../model/author";
import {Page} from "../../model/page";
import {AuthorService} from "../author.service";
import {PaginationService} from "../../pagination/pagination.service";

@Component({
  selector: 'app-authors-pagination-table',
  templateUrl: './authors-pagination-table.component.html',
  styleUrls: ['./authors-pagination-table.component.scss']
})
export class AuthorsPaginationTableComponent implements OnInit {

  page: Page<Author> = new Page()
  id: number

  constructor(
      private authorService: AuthorService,
      private paginationService: PaginationService
  ) { }

  ngOnInit(): void {
    this.getData()
  }

  private getData(): void {
    this.authorService.getPage(this.page.pageable)
        .subscribe(page => {
          console.log('This page = ', page)
          this.page = page
          console.log('This.page = ', this.page.content)
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
  public getNextPage(): void {
    this.page.pageable = this.paginationService.getNextPage(this.page);
    this.getData();
  }

  public getPreviousPage(): void {
    this.page.pageable = this.paginationService.getPreviousPage(this.page);
    this.getData();
  }

  public getPageInNewSize(pageSize: number): void {
    this.page.pageable = this.paginationService.getPageInNewSize(this.page, pageSize);
    this.getData();
  }

  setId(id: number) {
    this.id = id
  }

  getId() : number {
    return this.id;
  }
}
