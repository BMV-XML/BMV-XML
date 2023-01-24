import {Component, EventEmitter, Output} from '@angular/core';
import {EntityDto} from "../../../patent/models/entity-dto";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {
    capitalFirstLetterText,
    capitalFirstLetterTextWithSpace, emailValidator, phoneValidator, postalNumber,
    stringAndNumber,
    stringValidator
} from "../../../patent/validators";
import {MatRadioChange} from "@angular/material/radio";

@Component({
    selector: 'app-applicant',
    templateUrl: './applicant.component.html',
    styleUrls: ['./applicant.component.scss']
})
export class ApplicantComponent {
    id: number = 0
    person: boolean = true
    applicant: EntityDto = {
        id:0, city: "", country: "", fax: "", email: "", number: "", postalNumber: "", street: "", name: "",
        citizenship: "", businessName: "", surname: "", phone: "", person: false, completed: false
    }

    @Output() previousApplicant = new EventEmitter<EntityDto>()
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
            if (this.person && this.nameFormControl.valid && this.surnameFormControl.valid && this.citizenshipFormControl.valid) {
                this.applicant.businessName
                this.setApplicant(true);
                this.previousApplicant.emit(this.applicant)
            } else if (!this.person && this.businessNameFormControl.valid) {
                this.setApplicant(true);
                this.previousApplicant.emit(this.applicant);
            } else {
                this.setApplicant(false);
                this.previousApplicant.emit(this.applicant);
            }
        } else {
            this.setApplicant(false)
            this.previousApplicant.emit(this.applicant);
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


    private setApplicant(completed: boolean) {
        console.log("   ------------------ id ------------")
        console.log(this.id)
        this.applicant = {
            id:this.id,
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
