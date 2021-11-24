import {Injectable} from '@angular/core';
import {SortableColumn} from "../model/sortable-column";

@Injectable({
    providedIn: 'root'
})
export class SortingService {

    constructor() {
    }

    public changeSortableStateColumn(column: SortableColumn,
                                     sortingColumns: Array<SortableColumn>): Array<SortableColumn> {
        let sortedColumns = sortingColumns.filter(c => c.name != column.name);
        if (column.direction != null) {
            sortedColumns.unshift(column);
        }
        return sortedColumns;
    }

}
