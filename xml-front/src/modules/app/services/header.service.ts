import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class HeaderService {

  constructor(private router: Router) { }

  logout(){
    localStorage.removeItem('username')
    localStorage.removeItem('password')
    localStorage.removeItem('type')
    this.router.navigate(["auth/login"])
  }
}
