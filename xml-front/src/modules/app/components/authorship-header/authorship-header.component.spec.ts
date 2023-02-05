import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorshipHeaderComponent } from './authorship-header.component';

describe('AuthorshipHeaderComponent', () => {
  let component: AuthorshipHeaderComponent;
  let fixture: ComponentFixture<AuthorshipHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorshipHeaderComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthorshipHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
