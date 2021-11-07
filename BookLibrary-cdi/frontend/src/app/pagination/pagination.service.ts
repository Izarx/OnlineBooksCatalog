import {Injectable} from '@angular/core';
import {Pageable} from "../model/pagable";
import {Page} from "../model/page";

@Injectable({
  providedIn: 'root'
})
export class PaginationService {

  constructor() { }

  public getNextPage(page: Page<any>): Pageable {
    if(!page.last) {
      page.pageable.pageNumber = page.pageable.pageNumber+1;
    }
    return page.pageable;
  }

  public getPreviousPage(page: Page<any>): Pageable {
    if(!page.first) {
      page.pageable.pageNumber = page.pageable.pageNumber-1;
    }
    return page.pageable;
  }

  public getPageInNewSize(page: Page<any>, pageSize: number): Pageable {
    page.pageable.pageSize = pageSize;
    page.pageable.pageNumber = Pageable.FIRST_PAGE_NUMBER;

    return page.pageable;
  }

  public getPageNewNumber(page: Page<any>, pageNumber: number): Pageable {
    page.pageable.pageNumber = pageNumber;
    return page.pageable;
  }
}
