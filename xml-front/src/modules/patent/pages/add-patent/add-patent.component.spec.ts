import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPatentComponent } from './add-patent.component';

describe('AddPatentComponent', () => {
  let component: AddPatentComponent;
  let fixture: ComponentFixture<AddPatentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddPatentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddPatentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
