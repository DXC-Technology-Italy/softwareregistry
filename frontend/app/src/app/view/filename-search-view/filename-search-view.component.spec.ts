import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilenameSearchViewComponent } from './filename-search-view.component';

describe('FilenameSearchViewComponent', () => {
  let component: FilenameSearchViewComponent;
  let fixture: ComponentFixture<FilenameSearchViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FilenameSearchViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FilenameSearchViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
