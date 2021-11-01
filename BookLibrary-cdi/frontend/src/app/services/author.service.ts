import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Author} from "../authors/model/author";
import {Observable} from "rxjs";

const baseUrl = 'http://localhost:4200/api/authors';

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
}
