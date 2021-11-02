import { Component, OnInit } from '@angular/core';
import {Author} from "../model/author";
import {AuthorService} from "../author.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-create-author',
  templateUrl: './create-author.component.html',
  styleUrls: ['./create-author.component.scss']
})
export class CreateAuthorComponent implements OnInit {

  form: FormGroup = new FormGroup({})
  author: Author = new Author(0, '', '')

  constructor(private authorService : AuthorService) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      firstName: new FormControl('', [
          Validators.pattern("[a-zA-Z][a-zA-Z ]+"),
          Validators.required
      ]),
      lastName: new FormControl('', [
          Validators.pattern("[a-zA-Z][a-zA-Z ]+"),
          Validators.required
      ])
    })
  }

    createAuthor(author: Author) : void {
    this.authorService.createAuthor(author).subscribe(
        author => {
          this.author = author;
        })
  }

  submit() {
    if (this.form.valid) {
      const formData = {...this.form.value}
      this.author.authorId = null;
      this.author.firstName = formData.firstName;
      this.author.lastName = formData.lastName;
      this.form.reset();
      this.createAuthor(this.author)
    }
  }
}
