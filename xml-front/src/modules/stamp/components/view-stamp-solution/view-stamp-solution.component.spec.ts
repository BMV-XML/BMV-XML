import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewStampSolutionComponent } from './view-stamp-solution.component';

describe('ViewStampSolutionComponent', () => {
  let component: ViewStampSolutionComponent;
  let fixture: ComponentFixture<ViewStampSolutionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewStampSolutionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewStampSolutionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
