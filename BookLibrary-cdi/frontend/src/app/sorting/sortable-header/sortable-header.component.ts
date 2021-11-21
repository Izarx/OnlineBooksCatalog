import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SortableColumn} from "../../model/sortable-column";

@Component({
  selector: 'app-sortable-header',
  templateUrl: './sortable-header.component.html',
  styleUrls: ['./sortable-header.component.scss']
})
export class SortableHeaderComponent implements OnInit {

  @Input() sortableColumns: Array<SortableColumn>;
  @Output() sortEvent: EventEmitter<SortableColumn> = new EventEmitter<SortableColumn>();

  constructor() { }

  ngOnInit(): void {
  }

  sort(column: SortableColumn): void {
    column.toggleDirection();
    this.sortEvent.emit(column);
  }

}
