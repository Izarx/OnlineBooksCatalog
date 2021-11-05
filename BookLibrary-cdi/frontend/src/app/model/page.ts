import {Pageable} from "./pagable";
import {Sort} from "./sort";

export class Page<T> {
    content: Array<T>;
    pageable: Pageable;
    last: boolean;
    totalPages: number;
    totalElements: number = 8;
    first: boolean;
    sort: Sort;
    numberOfElements: number = 5;
    size: number;
    number: number;

    public constructor() {
        this.pageable = new Pageable();
    }
}