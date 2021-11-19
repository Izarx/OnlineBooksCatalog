import { Injectable } from '@angular/core';
import {SortableColumn} from "../model/sortable-column";

@Injectable({
  providedIn: 'root'
})
export class SortingService {

  constructor() { }

  public getSortableColumns(sortableColumns: Array<SortableColumn>): Array<SortableColumn> {
    return sortableColumns.filter(
        column => column.direction != null
    );
  }

  public changeSortableStateColumn (column: SortableColumn,
                                    sortableColumns: Array<SortableColumn>) {
    sortableColumns.find(col => col.name == column.name).direction = column.direction;
  }

}
