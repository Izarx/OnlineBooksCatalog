import {Injectable} from '@angular/core';
import {RequestOptions} from "../model/request-options";

@Injectable({
    providedIn: 'root'
})
export class PaginationService {

    constructor() {
    }

    public getNextPage(requestPage: RequestOptions): RequestOptions {
        if (!requestPage.last) {
            requestPage.pageNumber = requestPage.pageNumber + 1;
        }
        return requestPage;
    }

    public getPreviousPage(requestPage: RequestOptions): RequestOptions {
        if (!requestPage.first) {
            requestPage.pageNumber = requestPage.pageNumber - 1;
        }
        return requestPage;
    }

    public getPageInNewSize(requestPage: RequestOptions, pageSize: number): RequestOptions {
        requestPage.pageSize = pageSize;
        requestPage.pageNumber = RequestOptions.FIRST_PAGE_NUMBER;

        return requestPage;
    }

    public getPageNewNumber(requestPage: RequestOptions, pageNumber: number): RequestOptions {
        requestPage.pageNumber = pageNumber;
        return requestPage;
    }

    public initPageable(requestPage: RequestOptions, totalElements: number): RequestOptions {
        requestPage.totalElements = totalElements;
        let totalPages = totalElements != 0 ? Math.ceil(totalElements / requestPage.pageSize) : 1;
        requestPage.totalPages = totalPages;
        if (totalPages == 1) {
            requestPage.first = true;
            requestPage.last = true;
            requestPage.numberOfFirstPageElement = 1;
            requestPage.numberOfElements = totalElements;
        } else if (requestPage.pageNumber == 0) {
            requestPage.first = true;
            requestPage.last = false;
            requestPage.numberOfFirstPageElement = 1;
            requestPage.numberOfElements = requestPage.pageSize;
        } else if (requestPage.pageNumber + 1 == totalPages) {
            requestPage.first = false;
            requestPage.last = true;
            requestPage.numberOfFirstPageElement = requestPage.pageNumber * requestPage.pageSize + 1;
            requestPage.numberOfElements = totalElements;
        } else {
            requestPage.first = false;
            requestPage.last = false;
            requestPage.numberOfFirstPageElement = requestPage.pageNumber * requestPage.pageSize + 1;
            requestPage.numberOfElements = (requestPage.pageNumber + 1) * requestPage.pageSize;
        }
        return requestPage;
    }
}
