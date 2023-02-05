import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAcceptedRequestComponent } from './view-accepted-request.component';

describe('ViewAcceptedRequestComponent', () => {
  let component: ViewAcceptedRequestComponent;
  let fixture: ComponentFixture<ViewAcceptedRequestComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewAcceptedRequestComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAcceptedRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
