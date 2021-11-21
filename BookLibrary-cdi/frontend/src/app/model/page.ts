import {PageConstructor} from "./page-constructor";

export class Page<T> {
    content: Array<T>;
    pageConstructor: PageConstructor;
    last: boolean;
    totalPages: number;
    totalElements: number;
    first: boolean;
    numberOfFirstPageElement: number;
    numberOfElements: number;

    public constructor() {
        this.pageConstructor = new PageConstructor();
    }
}