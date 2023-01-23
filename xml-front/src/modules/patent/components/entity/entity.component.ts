import {Component, EventEmitter, Output, SimpleChanges} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatRadioChange} from "@angular/material/radio";
import {EntityDto} from "../../models/entity-dto";
import {
  capitalFirstLetterText,
  capitalFirstLetterTextWithSpace, emailValidator, numberValidator, phoneValidator, postalNumber,
  stringAndNumber,
  stringValidator
} from "../../validators";

@Component({
  selector: 'app-entity',
  templateUrl: './entity.component.html',
  styleUrls: ['./entity.component.scss']
})
export class EntityComponent {

  person: boolean = true

  @Output() display = new EventEmitter<EntityDto>()
  nameFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  surnameFormControl = new FormControl('', [Validators.required, capitalFirstLetterText])
  citizenshipFormControl = new FormControl('', [Validators.required, stringValidator])
  businessNameFormControl = new FormControl('', [Validators.required, capitalFirstLetterTextWithSpace])
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

  check () : void {
    console.log("/////////////////////// Change ///////////////////////////")
    console.log(this.secondFormGroup.errors)
    console.log(this.secondFormGroup.valid)
    if (this.secondFormGroup.valid){
      console.log(this.businessNameFormControl.value)
      if (this.person && this.nameFormControl.valid && this.surnameFormControl.valid && this.citizenshipFormControl.valid)
        this.display.emit(this.createEntityDto(true));
      else if (!this.person && this.businessNameFormControl.valid)
        this.display.emit(this.createEntityDto(true));
      else
        this.display.emit(this.createEntityDto(false));
    } else{
      this.display.emit(this.createEntityDto(false));
      console.log(this.secondFormGroup.errors)
      console.log(this.secondFormGroup.valid)
    }
  }

  optionChanged($event: MatRadioChange) {
    if ($event.value === '1')
      this.person = true
    else if ($event.value === '2')
      this.person = false
    this.check()
  }

  private createEntityDto(completed: boolean): EntityDto {
    return {
      businessName: this.businessNameFormControl.value,
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
      person: this.person
    }
  }
}
