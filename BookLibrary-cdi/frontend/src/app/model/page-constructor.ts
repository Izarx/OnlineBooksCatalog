import {Pageable} from "./pagable";
import {SortableColumn} from "./sortable-column";

export class PageConstructor {
    pageable: Pageable;
    sorting: Array<SortableColumn>;

    constructor() {
        this.pageable = new Pageable();
        this.sorting = new Array<SortableColumn>();
    }
}