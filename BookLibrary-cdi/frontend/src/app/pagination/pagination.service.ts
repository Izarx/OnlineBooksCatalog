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
      page.pageConstructor.pageable.pageNumber = page.pageConstructor.pageable.pageNumber+1;
    }
    return page.pageConstructor.pageable;
  }

  public getPreviousPage(page: Page<any>): Pageable {
    if(!page.first) {
      page.pageConstructor.pageable.pageNumber = page.pageConstructor.pageable.pageNumber-1;
    }
    return page.pageConstructor.pageable;
  }

  public getPageInNewSize(page: Page<any>, pageSize: number): Pageable {
    page.pageConstructor.pageable.pageSize = pageSize;
    page.pageConstructor.pageable.pageNumber = Pageable.FIRST_PAGE_NUMBER;

    return page.pageConstructor.pageable;
  }

  public getPageNewNumber(page: Page<any>, pageNumber: number): Pageable {
    page.pageConstructor.pageable.pageNumber = pageNumber;
    return page.pageConstructor.pageable;
  }
}
