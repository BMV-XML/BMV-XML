import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-simple-header',
  templateUrl: './simple-header.component.html',
  styleUrls: ['./simple-header.component.scss']
})
export class SimpleHeaderComponent {

  constructor(private router: Router) {
  }

  login() {
    this.router.navigateByUrl("auth/login")
  }

  register() {
    this.router.navigateByUrl("auth/register")

  }
}
