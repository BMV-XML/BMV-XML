import { TestBed } from '@angular/core/testing';

import { AuthorshipService } from './authorship.service';

describe('AuthorshipService', () => {
  let service: AuthorshipService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthorshipService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
