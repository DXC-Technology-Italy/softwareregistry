import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DependenciesViewComponent } from './dependencies-view.component';


describe('DependenciesViewComponent', () => {
  let component: DependenciesViewComponent;
  let fixture: ComponentFixture<DependenciesViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DependenciesViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DependenciesViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
