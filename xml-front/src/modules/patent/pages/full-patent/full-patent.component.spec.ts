import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FullPatentComponent } from './full-patent.component';

describe('FullPatentComponent', () => {
  let component: FullPatentComponent;
  let fixture: ComponentFixture<FullPatentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FullPatentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FullPatentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
