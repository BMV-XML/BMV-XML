import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatentRequestsComponent } from './patent-requests.component';

describe('PatentRequestsComponent', () => {
  let component: PatentRequestsComponent;
  let fixture: ComponentFixture<PatentRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatentRequestsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatentRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
