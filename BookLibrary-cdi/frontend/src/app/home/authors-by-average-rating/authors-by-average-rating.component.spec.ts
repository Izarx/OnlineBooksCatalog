import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorsByAverageRatingComponent } from './authors-by-average-rating.component';

describe('AuthorsByRatingComponent', () => {
  let component: AuthorsByAverageRatingComponent;
  let fixture: ComponentFixture<AuthorsByAverageRatingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorsByAverageRatingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorsByAverageRatingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
