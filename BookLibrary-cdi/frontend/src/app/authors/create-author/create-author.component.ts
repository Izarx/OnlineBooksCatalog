import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Author} from "../../model/author";
import {AuthorService} from "../../services/author.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
    selector: 'app-create-author',
    templateUrl: './create-author.component.html',
    styleUrls: ['./create-author.component.scss']
})
export class CreateAuthorComponent implements OnInit {

    form: FormGroup = new FormGroup({});
    author: Author = new Author(null, '', '', 0.0);
    @Output() initParentPage: EventEmitter<any> = new EventEmitter<any>();

    constructor(private authorService: AuthorService) {
    }

    ngOnInit(): void {
        this.form = new FormGroup({
            firstName: new FormControl('', [
                Validators.required]),
            lastName: new FormControl('', [])
        });
    }

    createAuthor(): void {
        this.authorService.createAuthor(this.author).subscribe(
            author => {
                this.initParentPage.emit(null);
            },
            error => {
                console.log(error);
            });
    }

    submit(): void {
        if (this.form.valid) {
            const formData = {...this.form.value};
            this.author.firstName = formData.firstName.trim();
            this.author.lastName = formData.lastName.trim();
            this.createAuthor();
            document.getElementById('createAuthorModalCloseButton').click();
        }
    }

    cancel(): void {
        if (this.form.valid) {
            this.form.reset();
        }
    }
}
