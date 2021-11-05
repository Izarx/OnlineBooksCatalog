import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {AuthorsComponent} from './authors/authors.component';
import {BooksComponent} from './books/books.component';
import {ReviewsComponent} from './reviews/reviews.component';
import {HomeComponent} from './home/home.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app-routing.module";
import {HttpClientModule} from "@angular/common/http";
import { CreateAuthorComponent } from './authors/create-author/create-author.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PaginationComponent } from './pagination/pagination.component';
import { AuthorsPaginationTableComponent } from './authors/authors-pagination-table/authors-pagination-table.component';

@NgModule({
    declarations: [
        AppComponent,
        AuthorsComponent,
        BooksComponent,
        ReviewsComponent,
        HomeComponent,
        CreateAuthorComponent,
        PaginationComponent,
        AuthorsPaginationTableComponent
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
