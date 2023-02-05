import {Component, ComponentRef, EventEmitter, Output, ViewChild, ViewContainerRef} from '@angular/core';
import {FormBuilder, Validators} from "@angular/forms";
import {PatentService} from "../../services/patent.service";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {MessageService} from "primeng/api";
import {DateAdapter} from "@angular/material/core";
import {FilterDto} from "../../models/filter-dto";
import * as xml2js from "xml2js";
import {SearchBy} from "../../models/search-by";
import {COMMA, ENTER, SPACE} from "@angular/cdk/keycodes";
import {MatChipEditedEvent, MatChipInputEvent} from "@angular/material/chips";
import {FilterComponent} from "../filter/filter.component";
import {SelectDto} from "../../models/select-dto";
import {PatentDto} from "../../models/patent-dto";
import {EntityDto} from "../../models/entity-dto";

@Component({
  selector: 'app-search-filter',
  templateUrl: './search-filter.component.html',
  styleUrls: ['./search-filter.component.scss']
})
export class SearchFilterComponent {

  patents: PatentDto[] = [];
  lastFilter: FilterDto[] = []

  searchFormGroup = this._formBuilder.group({
    numberOperator: ['', []],
    number: ['', []],
    titleOperator: ['', []],
    title: ['', []],
    datumOperator: ['', []],
    chosenDate: [null, []],
    submitterOperator: ['', []],
    submitter: ['', []],
    inventorOperator: ['', []],
    inventor: ['', []],
    commissionerOperator: ['', []],
    commissioner: ['', []],
  })

  @ViewChild("viewContainerRef", { read: ViewContainerRef }) vcr!: ViewContainerRef;
  refList: ComponentRef<FilterComponent>[] = []

  filterFormGroup = this._formBuilder.group({
    type: ['', [Validators.required]],
    text: ['', [Validators.required]],
  })

  fruits: SearchBy[] = [];
  readonly separatorKeysCodes = [ENTER, COMMA, SPACE] as const;


  firstElement: FilterDto = {operator: "", type: "", value: ""}

  type_options: SelectDto[] = [
    {name: "Podnosilac", value: "pod_ime"},
    {name: "Punomoćnik", value: "pun_ime"},
    {name: "Pronalazač", value: "pro_ime"},
    {name: "Izdvojena", value: "child"},
    {name: "Dodatna", value: "additional"},
    {name: "Prava prvenstva", value: "sibling"},
    {name: "Naslov", value: "naslov"},
    {name: "Datum prijave", value: "datum_prijave"},
    {name: "Broj prijave", value: "broj_prijave"}
  ]
  operator_options: SelectDto[] = [ {name: 'i', value: "i"}, {name:'ili', value:'ili'}, {name:'ne', value:"ne"}]

  @Output() filterElements = new EventEmitter<FilterDto[]>()
  @Output() searchElements = new EventEmitter<SearchBy[]>()

  constructor(private patentService: PatentService,
              private router: Router, private _formBuilder: FormBuilder,
              private readonly messageService: MessageService) {}

  filterRequests() {
    console.log("-----------")
    let nextFilter : FilterDto[] = []
    let firstElement : FilterDto = {
      operator: "i",
      type: this.filterFormGroup.controls.type.value,
      value: this.filterFormGroup.controls.text.value
    }
    nextFilter.push(firstElement)
    for (let elem of this.refList){
      nextFilter.push(elem.instance.getFilterElements())
    }
    console.log(nextFilter)
    this.filterElements.emit(nextFilter);
    console.log(this.lastFilter)
  }


  add(event: MatChipInputEvent): void {
    const value = (event.value || '').trim();
    if (value) {
      this.fruits.push({name: value});
    }
    event.chipInput!.clear();
  }

  remove(fruit: SearchBy): void {
    const index = this.fruits.indexOf(fruit);

    if (index >= 0) {
      this.fruits.splice(index, 1);
    }
  }

  edit(fruit: SearchBy, event: MatChipEditedEvent) {
    const value = event.value.trim();
    if (!value) {
      this.remove(fruit);
      return;
    }
    const index = this.fruits.indexOf(fruit);
    if (index >= 0) {
      this.fruits[index].name = value;
    }
  }


  search() {
    this.searchElements.emit(this.fruits)
  }

  private handleResponse(res: string) {
    console.log("--------------")
    console.log(res)
    let parser = new xml2js.Parser();
    parser.parseString(res, (err, result) => {
      try {
        console.log("**********************")
        console.log(result['List']['item'][0])
        this.patents = []
        for (let i of result['List']['item'])
          this.patents.push(i)
      } catch (err) {
        this.patents = []
      }
    });

  }


  addFilterElement() {
    let ref = this.vcr.createComponent(FilterComponent)
    this.refList.push(ref)
    ref.instance.operator_options = this.operator_options;
    ref.instance.type_options = this.type_options;
  }

  removeFilterElement() {
    if (this.refList.length == 0)
      return
    const index = this.vcr.indexOf(this.refList[this.refList.length-1].hostView)
    if (index != -1) {this.vcr.remove(index); this.refList.pop()}
  }
}
