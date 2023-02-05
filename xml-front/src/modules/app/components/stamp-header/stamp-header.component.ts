import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {HeaderService} from "../../services/header.service";

@Component({
  selector: 'app-stamp-header',
  templateUrl: './stamp-header.component.html',
  styleUrls: ['./stamp-header.component.scss']
})
export class StampHeaderComponent {
    constructor(private router: Router, private headerService :HeaderService) {
    }

    logout(){
        this.headerService.logout()
    }

    openList() {
        this.router.navigate(['stamp/soluted'])
    }

    openAdd() {
        this.router.navigateByUrl('stamp/add')
    }

}
