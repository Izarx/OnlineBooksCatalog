import {ComponentFixture, TestBed} from '@angular/core/testing';

import {BooksPaginationTableComponent} from './books-pagination-table.component';

describe('BooksPaginationTableComponent', () => {
    let component: BooksPaginationTableComponent;
    let fixture: ComponentFixture<BooksPaginationTableComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [BooksPaginationTableComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(BooksPaginationTableComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
