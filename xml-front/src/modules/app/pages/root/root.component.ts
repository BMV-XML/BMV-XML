import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AUTHORSHIP, OFFICIAL, PATENT, STAMP, PATENT_OFFICIAL, STAMP_OFFICIAL, AUTHORSHIP_OFFICIAL} from "../../../auth/types";

@Component({
  selector: 'app-root',
  templateUrl: './root.component.html',
  styleUrls: ['./root.component.scss']
})
export class RootComponent {
  public role: string | null = ''

  constructor(private router: Router) {
    console.log("-------------------")
    console.log(localStorage.getItem('type'))
  }

  ngOnInit () : void {
    this.role = String(localStorage.getItem('type'))

    this.router.events.subscribe(() => {
      this.role = String(localStorage.getItem('type'))
    })
  }

  isPatent(){
    return this.role === PATENT
  }

  isStamp(){
    return this.role === STAMP
  }

  isOfficialStamp(){
    return this.role === STAMP_OFFICIAL
  }

  isAuthorship() {
    return this.role === AUTHORSHIP
  }

  isNoOne() {
    return this.role === 'null' || !this.role
  }

  isOfficialPatent() {
    return this.role === OFFICIAL || this.role === PATENT_OFFICIAL
  }

  isOfficialAuthorship() {
    return this.role === OFFICIAL || this.role === AUTHORSHIP_OFFICIAL
  }
}
