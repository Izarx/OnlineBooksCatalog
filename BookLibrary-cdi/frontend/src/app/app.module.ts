import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {CreateReviewComponent} from './reviews/create-review.component';
import {HomeComponent} from './home/home.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import {CreateAuthorComponent} from './authors/create-author/create-author.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {PaginationComponent} from './pagination/pagination.component';
import {AuthorsPaginationTableComponent} from './authors/authors-pagination-table/authors-pagination-table.component';
import {UpdateAuthorComponent} from './authors/update-author/update-author.component';
import {BooksPaginationTableComponent} from './books/books-pagination-table/books-pagination-table.component';
import {CreateBookComponent} from './books/create-book/create-book.component';
import {UpdateBookComponent} from './books/update-book/update-book.component';
import {SortableColumnHeaderComponent} from './sorting/sortable-column-header/sortable-column-header.component';
import {DetailBookComponent} from './books/detail-book/detail-book.component';
import {AuthorsFilteringComponent} from './authors/authors-filtering/authors-filtering.component';
import {BooksFilteringComponent} from './books/books-filtering/books-filtering.component';
import {DetailAuthorComponent} from './authors/detail-author/detail-author.component';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {NgMultiSelectDropDownModule} from 'ng-multiselect-dropdown';
import { BooksByAverageRatingComponent } from './home/books-by-average-rating/books-by-average-rating.component';
import { AuthorsByAverageRatingComponent } from './home/authors-by-average-rating/authors-by-average-rating.component';

@NgModule({
    declarations: [
        AppComponent,
        CreateReviewComponent,
        HomeComponent,
        CreateAuthorComponent,
        PaginationComponent,
        AuthorsPaginationTableComponent,
        UpdateAuthorComponent,
        BooksPaginationTableComponent,
        CreateBookComponent,
        UpdateBookComponent,
        SortableColumnHeaderComponent,
        DetailBookComponent,
        AuthorsFilteringComponent,
        BooksFilteringComponent,
        DetailAuthorComponent,
        BooksByAverageRatingComponent,
        AuthorsByAverageRatingComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutingModule,
        HttpClientModule,
        ReactiveFormsModule,
        NgbModule,
        BrowserAnimationsModule,
        NgbModule,
        NgMultiSelectDropDownModule.forRoot()
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
