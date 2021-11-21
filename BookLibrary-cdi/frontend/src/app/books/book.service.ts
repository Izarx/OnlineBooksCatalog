import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Page} from "../model/page";
import {Book} from "../model/book";
import {PageConstructor} from "../model/page-constructor";

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

  public getPage(pageConstructor: PageConstructor): Observable<Page<Book>> {
    return this.httpClient.post<Page<Book>>(baseUrl, pageConstructor);
  }

  public updateBook(book: Book) : Observable<any> {
    return this.httpClient.put(baseUrl + `/update`, book);
  }
}
