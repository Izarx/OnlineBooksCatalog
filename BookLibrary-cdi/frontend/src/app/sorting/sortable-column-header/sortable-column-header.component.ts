import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {SortableColumn} from "../../model/sortable-column";

@Component({
  selector: 'app-sortable-column-header',
  templateUrl: './sortable-column-header.component.html',
  styleUrls: ['./sortable-column-header.component.scss']
})
export class SortableColumnHeaderComponent implements OnInit {

  @Input() sortableColumn: SortableColumn;
  @Output() sortEvent: EventEmitter<SortableColumn> = new EventEmitter<SortableColumn>();

  constructor() { }

  ngOnInit(): void {
  }

  sort(column: SortableColumn): void {
    column.toggleDirection();
    this.sortEvent.emit(column);
  }

}
