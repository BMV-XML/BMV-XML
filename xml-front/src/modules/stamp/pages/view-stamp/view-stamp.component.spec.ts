import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewStampComponent } from './view-stamp.component';

describe('ViewStampComponent', () => {
  let component: ViewStampComponent;
  let fixture: ComponentFixture<ViewStampComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewStampComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewStampComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
