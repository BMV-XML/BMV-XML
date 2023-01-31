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
      id:0, city: "", country: "", fax: "", email: "", number: "", postalNumber: "", street: "", name: "",
      citizenship: "", surname: "", phone: "", alive: false, completed: false, yearOfDeath: ""
  }

  @Output() previousApplicant = new EventEmitter<AuthorDto>()
  nameFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  surnameFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  citizenshipFormControl = new FormControl('', [Validators.required, stringValidator])
  yearOfDeath =  new FormControl('', [Validators.required])
  secondFormGroup: FormGroup;

  constructor(private _formBuilder: FormBuilder) {
    this.secondFormGroup = this._formBuilder.group({
        street: ['', [Validators.required, capitalFirstLetterTextWithSpace]],
        number: ['', [Validators.required, stringAndNumber]],
        postalNumber: ['', [Validators.required, postalNumber]],
        city: ['', [Validators.required, capitalFirstLetterTextWithSpace]],
        country: ['', [Validators.required, capitalFirstLetterTextWithSpace]],
        phone: ['', [Validators.required, phoneValidator]],
        fax: ['', [Validators.required]],//numberValidator
        email: ['', [Validators.required, emailValidator]]
    });
  }

  check(): void {
    console.log("/////////////////////// Change ///////////////////////////")
    console.log(this.secondFormGroup.errors)
    console.log(this.secondFormGroup.valid)
    if (this.secondFormGroup.valid) {
        if (this.nameFormControl.valid && this.surnameFormControl.valid && this.citizenshipFormControl.valid) {
            this.setAuthor(true);
            this.previousApplicant.emit(this.author)
        }else {
            this.setAuthor(false);
            this.previousApplicant.emit(this.author);
        }
    } else {
        this.setAuthor(false)
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
        email: this.secondFormGroup.controls["email"].value,
        fax: this.secondFormGroup.controls["fax"].value,
        name: this.nameFormControl.value,
        number: this.secondFormGroup.controls["number"].value,
        phone: this.secondFormGroup.controls["phone"].value,
        postalNumber: this.secondFormGroup.controls["postalNumber"].value,
        street: this.secondFormGroup.controls["street"].value,
        surname: this.surnameFormControl.value,
        completed: completed,
        alive: this.alive,
        yearOfDeath: this.yearOfDeath.value
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
