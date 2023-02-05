import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FullStampComponent } from './full-stamp.component';

describe('FullStampComponent', () => {
  let component: FullStampComponent;
  let fixture: ComponentFixture<FullStampComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FullStampComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FullStampComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
