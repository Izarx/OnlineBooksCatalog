import {NgModule} from "@angular/core";
import {RouterModule, Routes} from "@angular/router";
import {HomeComponent} from "./home/home.component";
import {ReviewsComponent} from "./reviews/reviews.component";
import {AuthorsPaginationTableComponent} from "./authors/authors-pagination-table/authors-pagination-table.component";
import {BooksPaginationTableComponent} from "./books/books-pagination-table/books-pagination-table.component";

const routes: Routes = [
    {path: '', component: HomeComponent},
    {path: 'authors', component: AuthorsPaginationTableComponent},
    {path: 'books', component: BooksPaginationTableComponent},
    {path: 'reviews', component: ReviewsComponent}
]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}