import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfficialHeaderComponent } from './official-header.component';

describe('OfficialHeaderComponent', () => {
  let component: OfficialHeaderComponent;
  let fixture: ComponentFixture<OfficialHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfficialHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OfficialHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
