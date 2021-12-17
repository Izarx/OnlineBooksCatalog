import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {AuthorsPaginationTableComponent} from "./authors/authors-pagination-table/authors-pagination-table.component";
import {BooksPaginationTableComponent} from "./books/books-pagination-table/books-pagination-table.component";
import {DetailBookComponent} from "./books/detail-book/detail-book.component";
import {DetailAuthorComponent} from "./authors/detail-author/detail-author.component";

const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'authors', component: AuthorsPaginationTableComponent},
    {path: 'books', component: BooksPaginationTableComponent},
    {path: 'books/:bookId', component: DetailBookComponent},
    {path: 'authors/:authorId', component: DetailAuthorComponent},
    {path: 'books/rating/:rating', component: BooksPaginationTableComponent},
    {path: 'authors/rating/:rating', component: AuthorsPaginationTableComponent}
]

@NgModule({
    imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
    exports: [RouterModule]
})
export class AppRoutingModule {

}