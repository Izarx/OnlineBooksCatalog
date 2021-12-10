import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {RequestOptions} from "../model/request-options";
import {ReviewFilter} from "../model/review-filter";
import {ResponseData} from "../model/response-data";
import {Review} from "../model/review";

const baseUrl = 'http://localhost:8080/api/reviews';

@Injectable({
    providedIn: 'root'
})
export class ReviewService {

    constructor(private httpClient: HttpClient) {
    }

    getPage(appRequestPage: RequestOptions<ReviewFilter>): Observable<ResponseData<Review>> {
        return this.httpClient.post<ResponseData<Review>>(baseUrl, appRequestPage);
    }

    createReview(review: Review): Observable<Review> {
        return this.httpClient.post<Review>(baseUrl + `/create`, review);
    }
}
