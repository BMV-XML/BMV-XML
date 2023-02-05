import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfficialAuthorshipHeaderComponent } from './official-authorship-header.component';

describe('OfficialAuthorshipHeaderComponent', () => {
  let component: OfficialAuthorshipHeaderComponent;
  let fixture: ComponentFixture<OfficialAuthorshipHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfficialAuthorshipHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OfficialAuthorshipHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
