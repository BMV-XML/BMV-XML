import {Component, EventEmitter, Output} from '@angular/core';
import {MatDatepickerInputEvent} from "@angular/material/datepicker";
import {DateAdapter} from "@angular/material/core";
import {countries} from "../countries";
import {FormControl, Validators} from "@angular/forms";
import {CountryDto} from "../../models/country-dto";
import {MatSelectChange} from "@angular/material/select";
import {PreviousPatentDto} from "../../models/previous-patent-dto";
import {applicationNumberValidator} from "../../validators";
import {PatentService} from "../../services/patent.service";

@Component({
  selector: 'app-patent',
  templateUrl: './patent.component.html',
  styleUrls: ['./patent.component.scss']
})
export class PatentComponent {

  id: number | undefined
  @Output() previousPatent = new EventEmitter<PreviousPatentDto>()
  patent: PreviousPatentDto = { id: 0, applicationNumber: "", country: "", submissionDate: '', completed: false }
  countries: CountryDto[];
  applicationNumber: FormControl = new FormControl('', [Validators.required, applicationNumberValidator])
  dateIsInPast: boolean = true;

  constructor(private patentService: PatentService,
    private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale('sr-Latn');
    this.countries = countries
  }

  check() {
    console.log("------ check -----")
    console.log(this.applicationNumber.valid)
    console.log(this.applicationNumber.status)
    console.log(this.applicationNumber.errors)
    console.log(this.patent.country)
    console.log(this.patent.submissionDate)
    if (this.applicationNumber.valid && this.patent.country !== "" && this.patent.submissionDate !== null){
      this.patent.applicationNumber = this.applicationNumber.value
      this.patent.completed = true
      this.previousPatent.emit(this.patent)
    }else{
      this.patent.completed = false
      this.previousPatent.emit(this.patent)
    }
  }

  dateChanged($event: MatDatepickerInputEvent<Date, Date | null>) {
    console.log("---------------------------------------------- Date Changed ----------------")
    console.log(this.patent.submissionDate)
    if ($event.value !== null && $event.value < new Date()){
      this.dateIsInPast = true
    }else{
      this.dateIsInPast = false
    }
    this.patent.submissionDate = this.patentService.convertDateToStringInPatent($event.value?.toLocaleString())//$event.value// new Date($event.value)
    console.log(this.patent.submissionDate)
    this.check()
  }

  countrySelectChanged($event: MatSelectChange) {
    this.patent.country = $event.value
    this.check()
  }
}
