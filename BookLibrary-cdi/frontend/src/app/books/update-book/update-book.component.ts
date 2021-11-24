import {Component, Input, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {Book} from "../../model/book";

@Component({
    selector: 'app-update-book',
    templateUrl: './update-book.component.html',
    styleUrls: ['./update-book.component.scss']
})
export class UpdateBookComponent implements OnInit {

    form: FormGroup = new FormGroup({})
    @Input() book: Book

    constructor() {
    }

    ngOnInit(): void {
    }

}
