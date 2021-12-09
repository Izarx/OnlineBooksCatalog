import {Component, ElementRef, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {AuthorService} from "../authors/author.service";
import {FormControl} from "@angular/forms";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";
import {Author} from "../model/author";
import {RequestOptions} from "../model/request-options";
import {AuthorFilter} from "../model/author-filter";


@Component({
  selector: 'app-chips-autocomplete',
  templateUrl: './chips-autocomplete.component.html',
  styleUrls: ['./chips-autocomplete.component.scss']
})
export class ChipsAutocompleteComponent implements OnInit {

  @ViewChild("authorInput") private _authorInput: ElementRef;
  @Output() initAuthors: EventEmitter<Array<Author>> = new EventEmitter<Array<Author>>()

  requestOptions: RequestOptions<AuthorFilter> = new RequestOptions<AuthorFilter>();
  authorFilter: AuthorFilter = new AuthorFilter(null, null, null);
  authorControl = new FormControl();
  authors: Author[] =[];
  selectedAuthors: Author[] = new Array<Author>();

  filteredAuthors: Observable<Author[]>;

  lastFilter: string = "";

  constructor(private authorService: AuthorService) {
  }

  ngOnInit(): void {
    this.requestOptions.pageSize = 10;
    this.filteredAuthors = this.authorControl.valueChanges.pipe(
        map(filter => this.filter(filter))
    );
  }

  filter(filter: string): Author[] {
    console.log(filter);
    this.lastFilter = filter;
    if (filter) {
      this.authorFilter.name = filter;
      this.requestOptions.filteredEntity = this.authorFilter;
      this.authorService.getPage(this.requestOptions).subscribe(
          page => {
            this.authors = page.content;
          }
      );

      return this.authors
    } else {
      return this.authors.slice();
    }
  }

  displayFn(value: Author[] | string): string | undefined {
    let displayValue: string;
    if (Array.isArray(value)) {
      value.forEach((author, index) => {
        if (index === 0) {
          displayValue = author.firstName + " " + author.lastName;
        } else {
          displayValue += ", " + author.firstName + " " + author.lastName;
        }
      });
    } else {
      displayValue = value;
    }
    return displayValue;
  }

  optionClicked(event: Event, author: Author) {
    event.stopPropagation();
    this.toggleSelection(author);
    // this.setAuthors(this.authors)
  }

  setAuthors(authors: Author[]) {
    this.initAuthors.emit(authors);
  }

  toggleSelection(author: Author) {
    author.isChecked = !author.isChecked;
    if (author.isChecked) {
      this.selectedAuthors.push(author);
    } else {
      const i = this.selectedAuthors.findIndex(
          value =>
              value.firstName === author.firstName && value.lastName === author.lastName
      );
      this.selectedAuthors.splice(i, 1);
    }

    // this.authorControl.setValue(this.selectedAuthors);
    this.authorControl.setValue(null);
    //this._authorInput.nativeElement.focus();
  }
}
