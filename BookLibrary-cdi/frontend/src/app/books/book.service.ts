import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AppResponsePage} from "../model/app-response-page";
import {Book} from "../model/book";
import {AppRequestPage} from "../model/app-request-page";

const baseUrl = 'http://localhost:8080/api/books';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(private httpClient: HttpClient) { }

  public createBook(book: Book) : Observable<Book> {
    return this.httpClient.post<Book>(baseUrl + `/create`, book);
  }

  public deleteBook(bookId : number) : Observable<void> {
    return this.httpClient.delete<void>(baseUrl + `/delete/${bookId}`);
  }

  public getPage(appRequestPage: AppRequestPage): Observable<AppResponsePage<Book>> {
    return this.httpClient.post<AppResponsePage<Book>>(baseUrl, appRequestPage);
  }

  public updateBook(book: Book) : Observable<any> {
    return this.httpClient.put(baseUrl + `/update`, book);
  }
}
