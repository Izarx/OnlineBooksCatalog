import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {AuthorFilter} from "../../model/author-filter";

@Component({
  selector: 'app-authors-filtering',
  templateUrl: './authors-filtering.component.html',
  styleUrls: ['./authors-filtering.component.scss']
})
export class AuthorsFilteringComponent implements OnInit {
  @Input() authorFilter: AuthorFilter;
  @Output() initFilteredAuthor: EventEmitter<AuthorFilter> = new EventEmitter<AuthorFilter>()
  authorFilteringForm: FormGroup = new FormGroup({});

  constructor() { }

  ngOnInit(): void {
    this.authorFilteringForm = new FormGroup({});
  }

  searchFilteredAuthors(authorFilter: AuthorFilter) {
    if (authorFilter.authorRatingFrom === null) {
      authorFilter.authorRatingFrom = 0.00;
    }
    this.initFilteredAuthor.emit(authorFilter);
  }

  reset() {
    this.authorFilter.name = null;
    this.authorFilter.authorRatingFrom = null;
    this.authorFilter.authorRatingTo = null;
  }
}
