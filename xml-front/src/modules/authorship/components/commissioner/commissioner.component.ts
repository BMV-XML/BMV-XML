import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { EntityDto } from 'modules/patent/models/entity-dto';
import { capitalFirstLetterText, capitalFirstLetterTextWithSpace, emailValidator, phoneValidator, postalNumber, stringAndNumber, stringValidator } from 'modules/patent/validators';

@Component({
  selector: 'app-commissioner',
  templateUrl: './commissioner.component.html',
  styleUrls: ['./commissioner.component.scss']
})
export class CommissionerComponent {

    person = true;
    @Input() commissioner: boolean = false;

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

    check(): void {
        console.log("/////////////////////// Change ///////////////////////////")
        console.log(this.secondFormGroup.errors)
        console.log(this.secondFormGroup.valid)
        if (this.secondFormGroup.valid) {
            console.log(this.businessNameFormControl.value)
            if (this.person && this.nameFormControl.valid && this.surnameFormControl.valid && this.citizenshipFormControl.valid)
                this.display.emit(this.createEntityDto(true));
            else
                this.display.emit(this.createEntityDto(false));
        } else {
            this.display.emit(this.createEntityDto(false));
            console.log(this.secondFormGroup.errors)
            console.log(this.secondFormGroup.valid)
        }
    }

    private createEntityDto(completed: boolean): EntityDto {
        return {
            id:0,
            businessName: '',
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
