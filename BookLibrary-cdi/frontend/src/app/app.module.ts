import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {ReviewsComponent} from './reviews/reviews.component';
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
import { CreateBookComponent } from './books/create-book/create-book.component';
import { UpdateBookComponent } from './books/update-book/update-book.component';

@NgModule({
    declarations: [
        AppComponent,
        ReviewsComponent,
        HomeComponent,
        CreateAuthorComponent,
        PaginationComponent,
        AuthorsPaginationTableComponent,
        UpdateAuthorComponent,
        BooksPaginationTableComponent,
        CreateBookComponent,
        UpdateBookComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutingModule,
        HttpClientModule,
        ReactiveFormsModule,
        NgbModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
