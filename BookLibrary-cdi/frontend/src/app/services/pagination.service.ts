import {Injectable} from '@angular/core';
import {RequestOptions} from "../model/request-options";

@Injectable({
    providedIn: 'root'
})
export class PaginationService<V> {

    constructor() {
    }

    public getNextPage(requestPage: RequestOptions<V>): RequestOptions<V> {
        if (!requestPage.last) {
            requestPage.pageNumber = requestPage.pageNumber + 1;
        }
        return requestPage;
    }

    public getPreviousPage(requestPage: RequestOptions<V>): RequestOptions<V> {
        if (!requestPage.first) {
            requestPage.pageNumber = requestPage.pageNumber - 1;
        }
        return requestPage;
    }

    public getPageInNewSize(requestPage: RequestOptions<V>, pageSize: number): RequestOptions<V> {
        requestPage.pageSize = pageSize;
        requestPage.pageNumber = RequestOptions.FIRST_PAGE_NUMBER;

        return requestPage;
    }

    public getPageNewNumber(requestPage: RequestOptions<V>, pageNumber: number): RequestOptions<V> {
        requestPage.pageNumber = pageNumber;
        return requestPage;
    }

    // todo: :( ?
    public initPageable(requestPage: RequestOptions<V>, totalElements: number): RequestOptions<V> {
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
        if (totalElements === 0) {
            requestPage.numberOfFirstPageElement = 0;
        }
        return requestPage;
    }
}
