import {Component, OnInit} from '@angular/core';
import {Author} from "../model/author";
import {AuthorService} from "./author.service";

const title: string = 'Authors'

@Component({
    selector: 'app-authors',
    templateUrl: './authors.component.html',
    styleUrls: ['./authors.component.scss']
})

export class AuthorsComponent implements OnInit {

    authors: Author[];
    id: number;
    page: number = 1;
    pageSize: number = 5;
    collectionSize: number;

    constructor(
        private authorService : AuthorService
        ) {}

    ngOnInit(): void {
        this.fetchAuthors()
    }

    fetchAuthors(): void {
        this.authorService.getAuthors().subscribe(
            authors => {
                this.authors = authors
                this.refreshAuthors()
            },
            error => {
                console.log(error);
            });
    }

    deleteAuthor(authorId : number) : void {
        this.authorService.deleteAuthor(authorId).subscribe(
            () => {
                this.fetchAuthors()
            },
            error => {
                console.log(error)
            })
    }

    refreshAuthors() {
        this.collectionSize = this.authors.length
        this.authors
            .map((author, i) => ({id: i + 1, ...author}))
            .slice((this.page - 1) * this.pageSize, (this.page - 1) * this.pageSize + this.pageSize);
    }

    setId(id: number) {
        this.id = id
    }

    getId() : number {
        return this.id;
    }
}