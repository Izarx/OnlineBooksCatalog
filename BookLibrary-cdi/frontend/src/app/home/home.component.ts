import {Component, OnInit} from '@angular/core';
import {AuthorService} from "../authors/author.service";
import {BookService} from "../books/book.service";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

    static readonly title: string = 'Home';

    constructor(private authorService: AuthorService,
                private bookService: BookService) {
    }

    ngOnInit(): void {

    }



}
