import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {AuthorsPaginationTableComponent} from "./authors/authors-pagination-table/authors-pagination-table.component";
import {BooksPaginationTableComponent} from "./books/books-pagination-table/books-pagination-table.component";
import {ManageBookComponent} from "./books/manage-book/manage-book.component";
import {ManageAuthorComponent} from "./authors/manage-author/manage-author.component";

const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'authors', component: AuthorsPaginationTableComponent},
    {path: 'books', component: BooksPaginationTableComponent},
    {path: 'books/:bookId', component: ManageBookComponent},
    {path: 'authors/:authorId', component: ManageAuthorComponent}
]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}