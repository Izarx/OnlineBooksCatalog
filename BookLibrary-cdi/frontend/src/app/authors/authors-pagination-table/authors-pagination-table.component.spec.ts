import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AuthorsPaginationTableComponent} from './authors-pagination-table.component';

describe('AuthorsPaginationTableComponent', () => {
    let component: AuthorsPaginationTableComponent;
    let fixture: ComponentFixture<AuthorsPaginationTableComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [AuthorsPaginationTableComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(AuthorsPaginationTableComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
