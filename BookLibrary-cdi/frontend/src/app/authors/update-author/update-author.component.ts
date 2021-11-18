import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Author} from "../../model/author";
import {AuthorService} from "../author.service";
import {AuthorsPaginationTableComponent} from "../authors-pagination-table/authors-pagination-table.component";

@Component({
  selector: 'app-update-author',
  templateUrl: './update-author.component.html',
  styleUrls: ['./update-author.component.scss']
})
export class UpdateAuthorComponent implements OnInit {

  form: FormGroup = new FormGroup({})
  @Input() author: Author

  constructor(
      private authorService : AuthorService,
      private authors: AuthorsPaginationTableComponent
  ) { }

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

  updateAuthor(): void {
    this.authorService.updateAuthor(this.author).subscribe(
        author => {
          this.author = author;
          this.authors.ngOnInit()
        },
        error => {
          console.log(error)
        }
    )
  }

  submit() {
    if (this.form.valid) {
      const formData = {...this.form.value}
      this.author.firstName = formData.firstName;
      this.author.lastName = formData.lastName;
      this.updateAuthor();
      this.form.reset();
      document.getElementById('updateAuthorModalCloseButton').click()
    }
  }

  cancel() {
    if (this.form.valid) {
      this.form.reset()
    }
  }
}
