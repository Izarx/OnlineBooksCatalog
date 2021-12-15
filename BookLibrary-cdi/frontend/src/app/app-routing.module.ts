import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {AuthorsPaginationTableComponent} from "./authors/authors-pagination-table/authors-pagination-table.component";
import {BooksPaginationTableComponent} from "./books/books-pagination-table/books-pagination-table.component";
import {DetailBookComponent} from "./books/detail-book/detail-book.component";
import {DetailAuthorComponent} from "./authors/detail-author/detail-author.component";
import {BooksByAverageRatingComponent} from "./home/books-by-average-rating/books-by-average-rating.component";
import {AuthorsByAverageRatingComponent} from "./home/authors-by-average-rating/authors-by-average-rating.component";

const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'authors', component: AuthorsPaginationTableComponent},
    {path: 'books', component: BooksPaginationTableComponent},
    {path: 'books/:bookId', component: DetailBookComponent},
    {path: 'authors/:authorId', component: DetailAuthorComponent},
    {path: 'books/rating/:rating', component: BooksByAverageRatingComponent},
    {path: 'authors/rating/:rating', component: AuthorsByAverageRatingComponent}
]

@NgModule({
    imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
    exports: [RouterModule]
})
export class AppRoutingModule {

}