import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Author} from "../../model/author";
import {AuthorService} from "../author.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthorsPaginationTableComponent} from "../authors-pagination-table/authors-pagination-table.component";

@Component({
    selector: 'app-create-author',
    templateUrl: './create-author.component.html',
    styleUrls: ['./create-author.component.scss']
})
export class CreateAuthorComponent implements OnInit {

    form: FormGroup = new FormGroup({})
    author: Author = new Author(null, '', '', 0.0);
    @Output() initParentPage: EventEmitter<any> = new EventEmitter<any>()

    constructor(
        private authorService: AuthorService,
        private authors: AuthorsPaginationTableComponent
    ) {
    }

    ngOnInit(): void {
        this.form = new FormGroup({
            firstName: new FormControl('', [
                Validators.pattern("[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я ]+"),
                Validators.required
            ]),
            lastName: new FormControl('', [
                Validators.pattern("[a-zA-Zа-яА-Я][a-zA-Zа-яА-Я ]+")
            ])
        })
    }

    createAuthor(): void {
        this.authorService.createAuthor(this.author).subscribe(
            author => {
                this.author = author;
                this.initParentPage.emit(null)
            },
            error => {
                console.log(error)
            })
    }

    submit() {
        if (this.form.valid) {
            const formData = {...this.form.value}
            this.author.firstName = formData.firstName;
            this.author.lastName = formData.lastName;
            this.createAuthor();
            this.form.reset();
            document.getElementById('createAuthorModalCloseButton').click()
        }
    }

    cancel() {
        if (this.form.valid) {
            this.form.reset()
        }
    }
}
