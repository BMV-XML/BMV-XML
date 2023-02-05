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
  }

  addNewRequest() {
    this.router.navigate(["authorship/add"])
  }

  getSolutions() {
    this.router.navigate(['authorship/soluted'])
  }

}
