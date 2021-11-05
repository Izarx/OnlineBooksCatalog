import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Author} from "../model/author";
import {Observable} from "rxjs";
import {Pageable} from "../model/pagable";

const baseUrl = 'http://localhost:8080/api/authors';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  constructor(private httpClient: HttpClient) { }

  public getAuthors() : Observable<Author[]> {
    return this.httpClient.get<Author[]>(baseUrl)
  }

  public createAuthor(author: Author) : Observable<Author> {
    return this.httpClient.post<Author>(baseUrl + `/create`, author);
  }

  public deleteAuthor(authorId : number) : Observable<void> {
    return this.httpClient.delete<void>(baseUrl + `/delete/${authorId}`);
  }

  getPage(pageable: Pageable): Observable<Author[]> {
    let url = baseUrl
        + '?page=' + pageable.pageNumber
        + '&size=' + pageable.pageSize;
    return this.httpClient.get<Author[]>(url);
  }
}
