import {AfterViewInit, Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Author} from "./model/author";
import {AuthorService} from "./author.service";

@Component({
    selector: 'app-authors',
    templateUrl: './authors.component.html',
    styleUrls: ['./authors.component.scss']
})

export class AuthorsComponent implements OnInit, AfterViewInit {

    page: number = 1;
    pageSize: number = 5;
    authors: Author[];
    collectionSize: number;
    @Output() loaded: EventEmitter<boolean> = new EventEmitter();

    constructor(
        private authorService : AuthorService
        ) {}

    ngOnInit(): void {
        this.fetchAuthors()
    }

    ngAfterViewInit(): void {

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
                this.loaded.emit(true)
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

}