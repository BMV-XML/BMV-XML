import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {HeaderService} from "../../services/header.service";

@Component({
  selector: 'app-official-stamp-header',
  templateUrl: './official-stamp-header.component.html',
  styleUrls: ['./official-stamp-header.component.scss']
})
export class OfficialStampHeaderComponent {
    constructor(private router:Router, private headerService:HeaderService) {
    }

    logout() {
        this.headerService.logout()
    }
}
