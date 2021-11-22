import {Injectable} from '@angular/core';
import {Pageable} from "../model/pagable";

@Injectable({
  providedIn: 'root'
})
export class PaginationService {

  constructor() { }

  public getNextPage(pageable: Pageable): Pageable {
    if(!pageable.last) {
      pageable.pageNumber = pageable.pageNumber+1;
    }
    return pageable;
  }

  public getPreviousPage(pageable: Pageable): Pageable {
    if(!pageable.first) {
      pageable.pageNumber = pageable.pageNumber-1;
    }
    return pageable;
  }

  public getPageInNewSize(pageable: Pageable, pageSize: number): Pageable {
    pageable.pageSize = pageSize;
    pageable.pageNumber = Pageable.FIRST_PAGE_NUMBER;

    return pageable;
  }

  public getPageNewNumber(pageable: Pageable, pageNumber: number): Pageable {
    pageable.pageNumber = pageNumber;
    return pageable;
  }

  public initPageable(pageable: Pageable, totalElements: number) : Pageable{
    pageable.totalElements = totalElements;
    let totalPages = totalElements != 0 ? Math.ceil( totalElements / pageable.pageSize) : 1;
    pageable.totalPages = totalPages;
    if (totalPages == 1) {
      pageable.first = true;
      pageable.last = true;
      pageable.numberOfFirstPageElement = 1;
      pageable.numberOfElements = totalElements;
    } else if (pageable.pageNumber == 0) {
      pageable.first = true;
      pageable.last = false;
      pageable.numberOfFirstPageElement = 1;
      pageable.numberOfElements = pageable.pageSize;
    } else if (pageable.pageNumber + 1 == totalPages) {
      pageable.first = false;
      pageable.last = true;
      pageable.numberOfFirstPageElement = pageable.pageNumber * pageable.pageSize + 1;
      pageable.numberOfElements = totalElements;
    } else {
      pageable.first = false;
      pageable.last = false;
      pageable.numberOfFirstPageElement = pageable.pageNumber * pageable.pageSize + 1;
      pageable.numberOfElements = (pageable.pageNumber + 1) * pageable.pageSize;
    }
    return pageable;
  }
}
