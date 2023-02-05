import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HeaderService } from 'modules/app/services/header.service';
import { AUTHORSHIP_OFFICIAL } from 'modules/auth/types';

@Component({
  selector: 'app-official-authorship-header',
  templateUrl: './official-authorship-header.component.html',
  styleUrls: ['./official-authorship-header.component.scss']
})
export class OfficialAuthorshipHeaderComponent {

  requests: string;

  constructor(private headerService: HeaderService, private router: Router) {
    if (localStorage.getItem('type') === AUTHORSHIP_OFFICIAL)
      this.requests = "Autorska prava"
    else
      this.requests = "Zahtevi"
  }

  logout() {
    this.headerService.logout()
  }

  getToRequests() {
    if (localStorage.getItem("type") == AUTHORSHIP_OFFICIAL)
      this.router.navigate(["authorship/list"])
  }

}
