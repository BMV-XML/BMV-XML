import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddStampComponent } from './add-stamp.component';

describe('AddStampComponent', () => {
  let component: AddStampComponent;
  let fixture: ComponentFixture<AddStampComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddStampComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddStampComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
