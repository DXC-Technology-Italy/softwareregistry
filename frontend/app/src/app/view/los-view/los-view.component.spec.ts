import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LosViewComponent } from './los-view.component';

describe('LosViewComponent', () => {
  let component: LosViewComponent;
  let fixture: ComponentFixture<LosViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LosViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LosViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
