import {Component, Input} from '@angular/core';
import {SelectDto} from "../../models/select-dto";
import {FilterDto} from "../../models/filter-dto";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent {

  @Input() type_options: SelectDto[] = [];
  @Input() operator_options: SelectDto[] = [];

  filterFormGroup = this._formBuilder.group({
    operator: ['', Validators.required],
    type: ['', [Validators.required]],
    text: ['', [Validators.required]],
  })

  constructor(private _formBuilder: FormBuilder) {
  }

  ngOnInit(){
    console.log(this.type_options);
    console.log(this.operator_options);
  }

  getFilterElements(): FilterDto{
    return {
      operator: this.filterFormGroup.controls.operator.value,
      type: this.filterFormGroup.controls.type.value,
      value: this.filterFormGroup.controls.text.value}
  }
}
