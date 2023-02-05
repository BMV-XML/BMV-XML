import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InspectSolutionComponent } from './inspect-solution.component';

describe('InspectSolutionComponent', () => {
  let component: InspectSolutionComponent;
  let fixture: ComponentFixture<InspectSolutionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InspectSolutionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InspectSolutionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
