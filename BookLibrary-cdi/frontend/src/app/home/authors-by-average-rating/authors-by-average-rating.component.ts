import { Component, OnInit } from '@angular/core';
import {SortableColumn} from "../../model/sortable-column";
import {ResponseData} from "../../model/response-data";
import {Author} from "../../model/author";
import {RequestOptions} from "../../model/request-options";
import {AuthorFilter} from "../../model/author-filter";
import {ActivatedRoute, Params} from "@angular/router";
import {PaginationService} from "../../services/pagination.service";
import {SortingService} from "../../services/sorting.service";
import {AuthorService} from "../../services/author.service";

@Component({
  selector: 'app-authors-by-average-rating',
  templateUrl: './authors-by-average-rating.component.html',
  styleUrls: ['./authors-by-average-rating.component.scss']
})
export class AuthorsByAverageRatingComponent implements OnInit {

  page: ResponseData<Author>;
  requestOptions: RequestOptions<AuthorFilter>;
  sortableColumns: Array<SortableColumn> = [
    new SortableColumn('firstName', 'First Name', null),
    new SortableColumn('lastName', 'Last Name', null),
    new SortableColumn('authorRating', 'Rating', null),
  ];

  constructor(private route: ActivatedRoute,
              private authorService: AuthorService,
              private paginationService: PaginationService<AuthorFilter>,
              private sortingService: SortingService) { }

  ngOnInit(): void {
    this.page = new ResponseData();
    this.requestOptions = new RequestOptions();
    this.requestOptions.filteredEntity = new AuthorFilter(null, null, null);
    this.getData();
  }

  getData(): void {
    this.route.params.subscribe((params: Params) => {
      let rating = params.rating;
      let ratingFrom = rating - 0.5;
      let ratingTo = rating - 0.5 + 0.99;
      if (ratingFrom < 0) {
        ratingFrom = 0;
      }
      if (ratingTo > 5) {
        ratingTo = 5;
      }
          this.requestOptions.filteredEntity.ratingFrom = ratingFrom;
          this.requestOptions.filteredEntity.ratingTo = ratingTo;
          this.authorService.getPage(this.requestOptions).subscribe(
              page => {
                this.paginationService.initPageable(this.requestOptions, page.totalElements);
                this.page = page;
              },
              error => {
                console.log(error);
              });
        },
        error => {
          console.log(error);
        });
  }

  public sort(sortableColumn: SortableColumn): void {
    this.requestOptions.sorting = this.sortingService.changeSortableStateColumn(sortableColumn, this.requestOptions.sorting);
    this.getData();
  }

  public getNextPage(): void {
    this.requestOptions = this.paginationService.getNextPage(this.requestOptions);
    this.getData();
  }

  public getPreviousPage(): void {
    this.requestOptions = this.paginationService.getPreviousPage(this.requestOptions);
    this.getData();
  }

  public getPageInNewSize(pageSize: number): void {
    this.requestOptions = this.paginationService.getPageInNewSize(this.requestOptions, pageSize);
    this.getData();
  }

  public getPageNewNumber(pageNumber: number): void {
    this.requestOptions = this.paginationService.getPageNewNumber(this.requestOptions, pageNumber);
    this.getData();
  }

}
