import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorsWorkComponent } from './authors-work.component';

describe('AuthorsWorkComponent', () => {
  let component: AuthorsWorkComponent;
  let fixture: ComponentFixture<AuthorsWorkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorsWorkComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthorsWorkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
