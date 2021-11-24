import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SortableColumnHeaderComponent} from './sortable-column-header.component';

describe('SortableHeaderComponent', () => {
  let component: SortableColumnHeaderComponent;
  let fixture: ComponentFixture<SortableColumnHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SortableColumnHeaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SortableColumnHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
