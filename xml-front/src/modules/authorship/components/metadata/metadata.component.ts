import { Component, Input } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { FilterDto } from 'modules/patent/models/filter-dto';
import { SelectDto } from 'modules/patent/models/select-dto';

@Component({
  selector: 'app-metadata',
  templateUrl: './metadata.component.html',
  styleUrls: ['./metadata.component.scss']
})
export class MetadataComponent {

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
