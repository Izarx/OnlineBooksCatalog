import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AuthorsFilteringComponent} from './authors-filtering.component';

describe('BooksFilteringComponent', () => { // todo: author or book ?
    let component: AuthorsFilteringComponent;
    let fixture: ComponentFixture<AuthorsFilteringComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [AuthorsFilteringComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(AuthorsFilteringComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
