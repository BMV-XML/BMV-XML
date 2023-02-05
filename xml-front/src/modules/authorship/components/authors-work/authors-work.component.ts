import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { AuthorsWorkDto } from 'modules/authorship/models/authtorship-request-dto';
import { capitalFirstLetterText } from 'modules/patent/validators';

@Component({
  selector: 'app-authors-work',
  templateUrl: './authors-work.component.html',
  styleUrls: ['./authors-work.component.scss']
})
export class AuthorsWorkComponent {

  types: string[] = ["Književno djelo", "Muzičko djelo", "Likovno djelo", "Računarski program", "Drugo"]
  forms: string[] = ["Štampani tekst", "Optički disk", "Drugo"]
  
  madeInWorkRelationship: boolean = false;
  isRemade: boolean = false;

  @Output() display = new EventEmitter<AuthorsWorkDto>()

  titleFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  alternateTitleFormControl = new FormControl('', [capitalFirstLetterText])
  nameFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  surnameFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  wayOfUsageFormControl = new FormControl('', [Validators.required])
  remadeTitleFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  workTypeFormControl = new FormControl('', [Validators.required])
  workFormFormControl = new FormControl('', [Validators.required])

  formGroup: FormGroup;

  constructor() {
    this.formGroup = new FormGroup({
      title: this.titleFormControl,
      alternateTitle: this.alternateTitleFormControl,
      name: this.nameFormControl,
      surname: this.surnameFormControl,
      wayOfUsage: this.wayOfUsageFormControl,
      remadeTitle: this.remadeTitleFormControl,
      workType: this.workFormFormControl,
      workForm: this.workFormFormControl
    })
  }

  madeInWorkRelationshipChange($event: MatCheckboxChange) {
    this.madeInWorkRelationship = $event.checked;
  }

  remadeChange($event: MatCheckboxChange) {
    this.isRemade = $event.checked;
  }

  isValidBasic(): boolean {
    if (this.titleFormControl.valid && this.wayOfUsageFormControl.valid && this.workFormFormControl.valid && this.workTypeFormControl.valid)
      return true;
    return false;
  }

  isValidRemade(): boolean {
    if (this.nameFormControl.valid && this.surnameFormControl.valid && this.remadeTitleFormControl.valid)
      return true;
    return false;
  }

  check() {
    if(!this.isRemade) {
      if (this.isValidBasic()) {
        this.display.emit(this.createEntityDto(true));
      }
      else {
        this.display.emit(this.createEntityDto(false));
      }
    }
    else {
      if (this.isValidBasic() && this.isValidRemade()) {
          this.display.emit(this.createEntityDto(true));
      }
      else {
        this.display.emit(this.createEntityDto(false));
      }
    }
  }

  private createEntityDto(completed: boolean): AuthorsWorkDto {
    return {
      title: this.titleFormControl.value,
      alternateTitle: this.alternateTitleFormControl.value,
      name: this.nameFormControl.value,
      surname: this.surnameFormControl.value,
      wayOfUsage: this.wayOfUsageFormControl.value,
      remadeTitle: this.remadeTitleFormControl.value,
      type: this.workTypeFormControl.value,
      form: this.workFormFormControl.value,
      completed: completed,
      isRemade: this.isRemade,
      madeInWorkRelationship: this.madeInWorkRelationship
    }
}

}
