import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {AuthorFilter} from "../../model/author-filter";
import {RequestOptions} from "../../model/request-options";


@Component({
    selector: 'app-authors-filtering',
    templateUrl: './authors-filtering.component.html',
    styleUrls: ['./authors-filtering.component.scss']
})
export class AuthorsFilteringComponent implements OnInit {
    @Input() requestOptions: RequestOptions<AuthorFilter>;
    @Output() initFilteredAuthor: EventEmitter<RequestOptions<AuthorFilter>> = new EventEmitter<RequestOptions<AuthorFilter>>()
    authorFilteringForm: FormGroup = new FormGroup({});

    constructor() {
    }

    ngOnInit(): void {
        this.authorFilteringForm = new FormGroup({
            name: new FormControl(null, []),
            authorRatingFrom: new FormControl(null, []),
            authorRatingTo: new FormControl(null, [])
        });
    }

    searchFilteredAuthors(requestOptions: RequestOptions<AuthorFilter>): void {
        this.requestOptions.pageNumber = 0;
        this.initFilteredAuthor.emit(requestOptions);
    }

    reset(): void {
        this.requestOptions.filteredEntity = new AuthorFilter(null, null, null);
    }

    setRatingFrom() {
        const formData = {...this.authorFilteringForm.value};
        this.requestOptions.filteredEntity.ratingFrom = formData.authorRatingFrom;
    }

    setRatingTo() {
        const formData = {...this.authorFilteringForm.value};
        this.requestOptions.filteredEntity.ratingTo = formData.authorRatingTo;
    }
}
