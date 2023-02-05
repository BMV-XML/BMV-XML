import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorshipRequestsComponent } from './authorship-requests.component';

describe('AuthorshipRequestsComponent', () => {
  let component: AuthorshipRequestsComponent;
  let fixture: ComponentFixture<AuthorshipRequestsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AuthorshipRequestsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AuthorshipRequestsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
