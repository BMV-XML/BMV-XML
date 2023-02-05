import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HeaderService } from 'modules/app/services/header.service';

@Component({
  selector: 'app-authorship-header',
  templateUrl: './authorship-header.component.html',
  styleUrls: ['./authorship-header.component.scss']
})
export class AuthorshipHeaderComponent {

  constructor(private headerService: HeaderService, private router: Router) {
  }

  logout() {
    this.headerService.logout()
    this.router.navigate(["auth/login"])
  }

  addNewRequest() {
    this.router.navigate(["authorship/add"])
  }

  getSolutions() {
    return;
  }

}
