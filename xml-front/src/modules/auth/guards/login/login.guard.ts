import { Injectable } from '@angular/core'
import { Router, CanActivate } from '@angular/router'
import {OFFICIAL, PATENT, PATENT_OFFICIAL, STAMP} from "../../types";

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {
  constructor (public router: Router) {}

  canActivate (): boolean {
    if (localStorage.getItem('type') === PATENT) {
      this.router.navigate(['/patent/add'])
      return false
    }else if (localStorage.getItem('type') === STAMP) {
      //this.router.navigate(['/patent/add'])
      //return false
    }else if (localStorage.getItem('type') === PATENT_OFFICIAL) {
      this.router.navigate(['/patent/list'])
      return false
    }
    return true
  }
}
