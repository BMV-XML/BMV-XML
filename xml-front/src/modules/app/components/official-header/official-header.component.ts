import { Component } from '@angular/core';
import {HeaderService} from "../../services/header.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-official-header',
  templateUrl: './official-header.component.html',
  styleUrls: ['./official-header.component.scss']
})
export class OfficialHeaderComponent {

  constructor(private headerService: HeaderService, private router: Router) {
  }

  logout() {
    this.headerService.logout()
    //this.router.navigate(["auth/login"])
  }
}
