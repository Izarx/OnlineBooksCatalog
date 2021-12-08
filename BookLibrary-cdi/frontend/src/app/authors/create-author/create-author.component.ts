import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Author} from "../../model/author";
import {AuthorService} from "../author.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

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
    ) {}

    ngOnInit(): void {
        this.form = new FormGroup({
            firstName: new FormControl('', [
                Validators.pattern("[a-zA-Zа-яА-Я ]+"),
                Validators.required
            ]),
            lastName: new FormControl('', [
                Validators.pattern("[a-zA-Zа-яА-Я ]+")
            ])
        })
    }

    createAuthor(): void {
        this.authorService.createAuthor(this.author).subscribe(
            author => {
                this.initParentPage.emit(null)
            },
            error => {
                console.log(error)
            })
    }

    submit() {
        if (this.form.valid) {
            const formData = {...this.form.value}
            console.log(formData.firstName);
            this.author.firstName = formData.firstName.trim();
            this.author.lastName = formData.lastName.trim();
            console.log(this.author);
            this.createAuthor();
            document.getElementById('createAuthorModalCloseButton').click()
        }
    }

    cancel() {
        if (this.form.valid) {
            this.form.reset();
        }
    }
}
