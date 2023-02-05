import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfficialPatentHeaderComponent } from './official-patent-header.component';

describe('OfficialHeaderComponent', () => {
  let component: OfficialPatentHeaderComponent;
  let fixture: ComponentFixture<OfficialPatentHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OfficialPatentHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OfficialPatentHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
