import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PatentHeaderComponent } from './patent-header.component';

describe('PatentHeaderComponent', () => {
  let component: PatentHeaderComponent;
  let fixture: ComponentFixture<PatentHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PatentHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PatentHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
