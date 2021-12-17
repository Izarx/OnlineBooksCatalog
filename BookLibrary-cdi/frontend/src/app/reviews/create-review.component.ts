import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Review} from "../model/review";
import {Book} from "../model/book";
import {ReviewService} from "../services/review.service";

@Component({
    selector: 'app-create-review',
    templateUrl: './create-review.component.html',
    styleUrls: ['./create-review.component.scss']
})
export class CreateReviewComponent implements OnInit {

    @Input() book: Book;
    @Output() initParentPage: EventEmitter<any> = new EventEmitter<any>();
    form: FormGroup = new FormGroup({});
    review: Review = new Review(null, null, null, 0, null);

    constructor(private reviewService: ReviewService) {
    }

    ngOnInit(): void {
        this.review.rating = 0;
        this.form = new FormGroup({
            commenterName: new FormControl('', []),
            comment: new FormControl('', []),
            rating: new FormControl('', [])
        })
    }

    createReview(): void {
        this.reviewService.createReview(this.review).subscribe(
            review => {
                this.initParentPage.emit(null);
            },
            error => {
                console.log(error);
            })
    }

    submit(): void {
        const formData = {...this.form.value};
        this.review.book = this.book;
        let commenterName = formData.commenterName;
        if (commenterName !== null && commenterName !== undefined) {
            this.review.commenterName = commenterName.trim();
        }
        let comment = formData.comment;
        if (comment !== null && comment !== undefined) {
            this.review.comment = comment.trim();
        }
        this.review.rating = formData.rating;
        this.createReview();
        document.getElementById('createReviewModalCloseButton').click()
    }

    cancel(): void {
        this.form.reset();
    }
}
