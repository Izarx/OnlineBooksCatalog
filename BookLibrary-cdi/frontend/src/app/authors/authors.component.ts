import {Component, OnInit} from '@angular/core';
import {Author} from "./model/author";
import {AuthorService} from "../services/author.service";

@Component({
    selector: 'app-authors',
    templateUrl: './authors.component.html',
    styleUrls: ['./authors.component.scss']
})

export class AuthorsComponent implements OnInit {

    authors?: Author[]

    constructor(private authorService : AuthorService) {
    }

    ngOnInit(): void {
        this.fetchAuthors()
    }

    fetchAuthors(): void {
        this.authorService.getAuthors().subscribe(
            authors => {
                this.authors = authors
            },
            error => {
                console.log(error);
            });
    }

    deleteAuthor(authorId : number) : void {
        this.authorService.deleteAuthor(authorId)
            .subscribe(() => {
                this.fetchAuthors()
            },
                error => {
                console.log(error)
                })
    }
}
