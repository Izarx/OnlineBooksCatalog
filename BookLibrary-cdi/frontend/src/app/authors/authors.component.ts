import {Component, OnInit} from '@angular/core';
import {Author} from "./model/author";
import {AuthorService} from "./author.service";

@Component({
    selector: 'app-authors',
    templateUrl: './authors.component.html',
    styleUrls: ['./authors.component.scss']
})

export class AuthorsComponent implements OnInit {

    authors: Author[] | undefined;
    authorId: number | null | undefined = 0

    constructor(
        private authorService : AuthorService) {}

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

    deleteAuthor(author : Author) : void {
        this.authorId = author.authorId;
        if (this.authorId !== 0 && this.authorId !== undefined && this.authorId !== null) {
            this.authorService.deleteAuthor(this.authorId).subscribe(
                () => {
                    this.fetchAuthors()
                },
                error => {
                    console.log(error)
                })
        }
    }
}