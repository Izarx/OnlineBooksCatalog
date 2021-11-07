import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Page} from "../model/page";

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss']
})
export class PaginationComponent implements OnInit {

  @Input() page: Page<any>;
  @Output() nextPageEvent = new EventEmitter();
  @Output() previousPageEvent = new EventEmitter();
  @Output() pageSizeEvent: EventEmitter<number> = new EventEmitter<number>();
  @Output() pageNumberEvent: EventEmitter<number> = new EventEmitter<number>();

  constructor() {}

  ngOnInit(): void {
  }

  numberPagesInit() : number[] {
    return Array(this.page.totalPages).fill(0).map((x,i)=>i+1);
  }

  nextPage(): void {
    this.nextPageEvent.emit(null);
  }

  previousPage(): void {
    this.previousPageEvent.emit(null);
  }

  updatePageNumber(pageNumber: number): void {
    this.pageNumberEvent.emit(pageNumber);
  }

  updatePageSize(pageSize: number): void {
    this.pageSizeEvent.emit(pageSize);
  }

}
