import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAuthorshipComponent } from './add-authorship.component';

describe('AddAuthorshipComponent', () => {
  let component: AddAuthorshipComponent;
  let fixture: ComponentFixture<AddAuthorshipComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddAuthorshipComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddAuthorshipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
