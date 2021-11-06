import {Pageable} from "./pagable";

export class Page<T> {
    content: Array<T>;
    pageable: Pageable;
    last: boolean;
    totalPages: number;
    totalElements: number;
    first: boolean;
    numberOfElements: number;

    public constructor() {
        this.pageable = new Pageable();
    }
}