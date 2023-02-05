import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddStampSolutionComponent } from './add-stamp-solution.component';

describe('AddStampSolutionComponent', () => {
  let component: AddStampSolutionComponent;
  let fixture: ComponentFixture<AddStampSolutionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddStampSolutionComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddStampSolutionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
