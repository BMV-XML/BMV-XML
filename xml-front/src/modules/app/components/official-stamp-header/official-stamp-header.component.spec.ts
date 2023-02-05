import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfficialStampHeaderComponent } from './official-stamp-header.component';

describe('OfficialStampHeaderComponent', () => {
  let component: OfficialStampHeaderComponent;
  let fixture: ComponentFixture<OfficialStampHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfficialStampHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OfficialStampHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
