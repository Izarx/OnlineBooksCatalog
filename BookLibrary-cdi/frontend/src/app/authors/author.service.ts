import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Author} from "../model/author";
import {Observable} from "rxjs";
import {AppResponsePage} from "../model/app-response-page";
import {AppRequestPage} from "../model/app-request-page";

const baseUrl = 'http://localhost:8080/api/authors';

@Injectable({
    providedIn: 'root'
})
export class AuthorService {

    constructor(private httpClient: HttpClient) {
    }

    public createAuthor(author: Author): Observable<Author> {
        return this.httpClient.post<Author>(baseUrl + `/create`, author);
    }

    public deleteAuthor(authorId: number): Observable<void> {
        return this.httpClient.delete<void>(baseUrl + `/delete/${authorId}`);
    }

    public getPage(appRequestPage: AppRequestPage): Observable<AppResponsePage<Author>> {
        return this.httpClient.post<AppResponsePage<Author>>(baseUrl, appRequestPage);
    }

    public updateAuthor(author: Author): Observable<any> {
        return this.httpClient.put(baseUrl + `/update`, author);
    }
}
