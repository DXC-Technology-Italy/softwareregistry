import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DeliveryCheckViewComponent} from './delivery-check-view.component';

describe('DeliveryCheckViewComponent', () => {
  let component: DeliveryCheckViewComponent;
  let fixture: ComponentFixture<DeliveryCheckViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DeliveryCheckViewComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeliveryCheckViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
