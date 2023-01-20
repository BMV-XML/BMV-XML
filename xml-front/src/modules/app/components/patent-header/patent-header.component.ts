import { Component } from '@angular/core';
import {HeaderService} from "../../services/header.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-patent-header',
  templateUrl: './patent-header.component.html',
  styleUrls: ['./patent-header.component.scss']
})
export class PatentHeaderComponent {

  constructor(private headerService: HeaderService, private router: Router) {
  }

  logout() {
    this.headerService.logout()
    this.router.navigate(["auth/login"])
  }
}
