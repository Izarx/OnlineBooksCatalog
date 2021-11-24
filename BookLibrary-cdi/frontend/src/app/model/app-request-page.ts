import {SortableColumn} from "./sortable-column";

export class AppRequestPage {
    static readonly DEFAULT_PAGE_SIZE = 5;
    static readonly FIRST_PAGE_NUMBER = 0;
    pageSize: number;
    pageNumber: number;
    last?: boolean;
    totalPages?: number;
    first?: boolean;
    numberOfFirstPageElement?: number;
    numberOfElements?: number;
    totalElements?: number;
    sorting: Array<SortableColumn>;

    public constructor() {
        this.pageSize = AppRequestPage.DEFAULT_PAGE_SIZE;
        this.pageNumber = AppRequestPage.FIRST_PAGE_NUMBER;
        this.sorting = new Array<SortableColumn>();
    }
}