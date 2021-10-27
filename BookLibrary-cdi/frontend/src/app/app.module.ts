import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {AuthorsComponent} from './authors/authors.component';
import {BooksComponent} from './books/books.component';
import {ReviewsComponent} from './reviews/reviews.component';
import {HomeComponent} from './home/home.component';
import {FormsModule} from "@angular/forms";
import {AppRoutingModule} from "./app-routing.module";

@NgModule({
    declarations: [
        AppComponent,
        AuthorsComponent,
        BooksComponent,
        ReviewsComponent,
        HomeComponent,
    ],
    imports: [
        BrowserModule,
        FormsModule,
        AppRoutingModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
