import { Component } from '@angular/core';
import {HeaderService} from "../../services/header.service";
import {Router} from "@angular/router";
import {PATENT, PATENT_OFFICIAL} from "../../../auth/types";

@Component({
  selector: 'app-official-header',
  templateUrl: './official-header.component.html',
  styleUrls: ['./official-header.component.scss']
})
export class OfficialHeaderComponent {
  requests: string;

  constructor(private headerService: HeaderService, private router: Router) {
    if (localStorage.getItem('type') === PATENT_OFFICIAL)
      this.requests = "Patenti"
    else
      this.requests = "Zahtevi"
  }

  logout() {
    this.headerService.logout()
    //this.router.navigate(["auth/login"])
  }

  getToRequests() {
    if (localStorage.getItem("type") == PATENT_OFFICIAL)
      this.router.navigate(["patent/list"])
  }
}
