import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BooksByRatingComponent } from './books-by-rating.component';

describe('BooksByRatingComponent', () => {
  let component: BooksByRatingComponent;
  let fixture: ComponentFixture<BooksByRatingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BooksByRatingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BooksByRatingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
