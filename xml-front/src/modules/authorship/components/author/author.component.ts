import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatRadioChange } from '@angular/material/radio';
import { AuthorDto } from 'modules/authorship/models/author';
import { EntityDto } from 'modules/patent/models/entity-dto';
import { capitalFirstLetterText, capitalFirstLetterTextWithSpace, emailValidator, numberValidator, phoneValidator, postalNumber, stringAndNumber, stringValidator } from 'modules/patent/validators';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.scss']
})
export class AuthorComponent {

  id: number = 0
  alive: boolean = true
  author: AuthorDto = {
    id: 0, city: "", country: "", postalNumber: "", street: "", name: "",
    citizenship: "", surname: "", alive: false, completed: false, yearOfDeath: 0,
    number: null,
    pseudonym: ""
  }

  @Output() previousApplicant = new EventEmitter<AuthorDto>()
  @Output() validForm = new EventEmitter<boolean>()
  nameFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  surnameFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  citizenshipFormControl = new FormControl('', [Validators.required, stringValidator])
  yearOfDeath =  new FormControl(0, [Validators.required])
  pseudonymFormControl = new FormControl('')
  secondFormGroup: FormGroup;

  constructor(private _formBuilder: FormBuilder) {
    this.secondFormGroup = this._formBuilder.group({
        street: ['', [Validators.required, capitalFirstLetterTextWithSpace]],
        number: ['', [Validators.required, stringAndNumber]],
        postalNumber: ['', [Validators.required, postalNumber]],
        city: ['', [Validators.required, capitalFirstLetterTextWithSpace]],
        country: ['', [Validators.required, capitalFirstLetterTextWithSpace]],
    });
  }

  check(): void {
    console.log("/////////////////////// Change ///////////////////////////")
    console.log(this.secondFormGroup.errors)
    console.log(this.secondFormGroup.valid)

    if (this.secondFormGroup.valid) {
        if (this.nameFormControl.valid && this.surnameFormControl.valid && this.citizenshipFormControl.valid) {
            this.setAuthor(true);
            this.validForm.emit(true);
            this.previousApplicant.emit(this.author)
        }else {
            this.setAuthor(false);
            this.validForm.emit(false);
            this.previousApplicant.emit(this.author);
        }
    } else {
        this.setAuthor(false)
        this.validForm.emit(false);
        this.previousApplicant.emit(this.author);
        console.log(this.secondFormGroup.errors)
        console.log(this.secondFormGroup.valid)
    }
  }


  private setAuthor(completed: boolean) {
    console.log("   ------------------ id ------------")
    console.log(this.id)
    this.author = {
        id:this.id,
        citizenship: this.citizenshipFormControl.value,
        city: this.secondFormGroup.controls["city"].value,
        country: this.secondFormGroup.controls["country"].value,
        name: this.nameFormControl.value,
        number: this.secondFormGroup.controls["number"].value,
        postalNumber: this.secondFormGroup.controls["postalNumber"].value,
        street: this.secondFormGroup.controls["street"].value,
        surname: this.surnameFormControl.value,
        completed: completed,
        alive: this.alive,
        yearOfDeath: this.yearOfDeath.value,
        pseudonym: this.pseudonymFormControl.value
    }
  }

  optionChanged($event: MatRadioChange) {
    if ($event.value === '1')
      this.alive = true
    else if ($event.value === '2')
      this.alive = false
    this.check()
  }



}
