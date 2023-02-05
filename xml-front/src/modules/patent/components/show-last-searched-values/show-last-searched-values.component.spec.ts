import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowLastSearchedValuesComponent } from './show-last-searched-values.component';

describe('ShowLastSearchedValuesComponent', () => {
  let component: ShowLastSearchedValuesComponent;
  let fixture: ComponentFixture<ShowLastSearchedValuesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowLastSearchedValuesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowLastSearchedValuesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
