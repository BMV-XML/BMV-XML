import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorshipSolutionsComponent } from './authorship-solutions.component';

describe('AuthorshipSolutionsComponent', () => {
  let component: AuthorshipSolutionsComponent;
  let fixture: ComponentFixture<AuthorshipSolutionsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorshipSolutionsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthorshipSolutionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
