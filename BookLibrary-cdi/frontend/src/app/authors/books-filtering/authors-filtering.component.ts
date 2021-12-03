import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Author} from "../../model/author";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-authors-filtering',
  templateUrl: './authors-filtering.component.html',
  styleUrls: ['./authors-filtering.component.scss']
})
export class AuthorsFilteringComponent implements OnInit {
  @Input() filteredAuthor: Author;
  @Output() initFilteredAuthor: EventEmitter<Author> = new EventEmitter<Author>()
  authorFilteringForm: FormGroup = new FormGroup({});

  constructor() { }

  ngOnInit(): void {
    this.authorFilteringForm = new FormGroup({
      firstName: new FormControl('', []),
      lastName: new FormControl('', []),
      authorRating: new FormControl('', [
        Validators.min(0),
        Validators.max(5)
      ])
    });
  }

  searchFilteredAuthor(filteredAuthor: Author) {
    this.initFilteredAuthor.emit(filteredAuthor);
  }

  reset() {
    this.filteredAuthor.firstName = null;
    this.filteredAuthor.lastName = null;
    this.filteredAuthor.authorRating = null;
  }
}
