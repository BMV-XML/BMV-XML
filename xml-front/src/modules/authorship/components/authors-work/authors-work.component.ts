import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { capitalFirstLetterText } from 'modules/patent/validators';

@Component({
  selector: 'app-authors-work',
  templateUrl: './authors-work.component.html',
  styleUrls: ['./authors-work.component.scss']
})
export class AuthorsWorkComponent {

  types: string[] = ["Kniževno djelo", "Muzičko djelo", "Likovno djelo", "Računarski program", "Drugo"]
  forms: string[] = ["Štampani tekst", "Optički disk", "Drugo"]
  // form: string = ''
  madeInWorkRelationship: boolean = false;
  isRemade: boolean = false;

  titleFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  alternateTitleFormControl = new FormControl('', [capitalFirstLetterText])
  nameFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  surnameFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  wayOfUsageFormControl = new FormControl('', [Validators.required])
  remadeTitleFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  workTypeFormControl = new FormControl('', [Validators.required])
  workFormFormControl = new FormControl('', [Validators.required])

  constructor() {}

  madeInWorkRelationshipChange($event: MatCheckboxChange) {
    this.madeInWorkRelationship = $event.checked;
  }

  remadeChange($event: MatCheckboxChange) {
    this.isRemade = $event.checked;
  }

  check() {
    return true;
  }

}
