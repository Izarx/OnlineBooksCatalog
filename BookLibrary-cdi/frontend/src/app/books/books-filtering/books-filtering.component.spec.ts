import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BooksFilteringComponent} from './books-filtering.component';

describe('BooksFilteringngComponent', () => {
    let component: BooksFilteringComponent;
    let fixture: ComponentFixture<BooksFilteringComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [BooksFilteringComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(BooksFilteringComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
