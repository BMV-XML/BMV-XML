import {Component, EventEmitter, Output, SimpleChanges} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {MatRadioChange} from "@angular/material/radio";
import {EntityDto} from "../../models/entity-dto";

@Component({
  selector: 'app-entity',
  templateUrl: './entity.component.html',
  styleUrls: ['./entity.component.scss']
})
export class EntityComponent {

  person: boolean = true

  @Output() display = new EventEmitter<EntityDto>()
  nameFormControl = new FormControl('', [])
  surnameFormControl = new FormControl('', [])
  citizenshipFormControl = new FormControl('', [])
  businessNameFormControl = new FormControl('', [])
  secondFormGroup: FormGroup;


  constructor(private _formBuilder: FormBuilder) {
    this.secondFormGroup = this._formBuilder.group({
      street: ['', Validators.required],
      number: ['', Validators.required],
      postalNumber: ['', Validators.required],
      city: ['', Validators.required],
      country: ['', Validators.required],
      phone: ['', Validators.required],
      fax: ['', Validators.required],
      email: ['', Validators.required],
      name: this.nameFormControl,
      surname: this.surnameFormControl,
      citizenship: this.citizenshipFormControl,
      businessName: this.businessNameFormControl,
    });
  }

  check () : void {
    console.log("/////////////////////// Change ///////////////////////////")
    if (this.secondFormGroup.valid || this.secondFormGroup.errors === null){
      console.log(this.businessNameFormControl.value)
      if (this.person && this.nameFormControl.value !== null && this.surnameFormControl.value !== null && this.citizenshipFormControl.value !== null)
        this.display.emit(this.createEntityDto(true));
      else if (!this.person && this.businessNameFormControl.value !== null)
        this.display.emit(this.createEntityDto(true));
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
      completed: completed
    }
  }
}
