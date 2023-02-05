import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StampHeaderComponent } from './stamp-header.component';

describe('StampHeaderComponent', () => {
  let component: StampHeaderComponent;
  let fixture: ComponentFixture<StampHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StampHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StampHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
