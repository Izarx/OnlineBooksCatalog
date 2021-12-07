import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ResponseData} from "../model/response-data";
import {Book} from "../model/book";
import {RequestOptions} from "../model/request-options";
import {BookFilter} from "../model/book-filter";

const baseUrl = 'http://localhost:8080/api/books';

@Injectable({
    providedIn: 'root'
})
export class BookService {

    constructor(private httpClient: HttpClient) {
    }

    public createBook(book: Book): Observable<Book> {
        return this.httpClient.post<Book>(baseUrl + `/create`, book);
    }

    public deleteBook(bookId: number): Observable<void> {
        return this.httpClient.delete<void>(baseUrl + `/delete/${bookId}`);
    }

    public getPage(appRequestPage: RequestOptions<BookFilter>): Observable<ResponseData<Book>> {
        return this.httpClient.post<ResponseData<Book>>(baseUrl, appRequestPage);
    }

    public updateBook(book: Book): Observable<any> {
        return this.httpClient.put(baseUrl + `/update`, book);
    }

    getBookById(bookId: number) : Observable<Book> {
        return this.httpClient.get<Book>(baseUrl + `/${bookId}`);
    }

    getBookByIdWithAuthors(bookId: number) : Observable<Book> {
        return this.httpClient.get<Book>(baseUrl + `/authors/${bookId}`);
    }

    bulkDeleteBooks(booksIdsForDelete: number[]): Observable<Array<Book>> {
        return this.httpClient.post<Array<Book>>(baseUrl + `/bulk-delete`, booksIdsForDelete);
    }
}
