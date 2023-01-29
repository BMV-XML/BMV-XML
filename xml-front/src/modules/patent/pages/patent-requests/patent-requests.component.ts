import {Component, ComponentRef, ViewChild, ViewContainerRef} from '@angular/core';
import {PatentService} from "../../services/patent.service";
import * as xml2js from "xml2js";
import {PatentDto} from "../../models/patent-dto";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {AddSolutionComponent} from "../../components/add-solution/add-solution.component";
import {ViewSolutionComponent} from "../../components/view-solution/view-solution.component";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {FilterDto} from "../../models/filter-dto";
import {SearchBy} from "../../models/search-by";
import {MatChipEditedEvent, MatChipInputEvent} from "@angular/material/chips";
import {COMMA, ENTER, SPACE} from "@angular/cdk/keycodes";
import {PatentComponent} from "../../components/patent/patent.component";
import {FilterComponent} from "../../components/filter/filter.component";
import {SelectDto} from "../../models/select-dto";
import {MatSelect} from "@angular/material/select";
import {capitalFirstLetterTextWithSpace, postalNumber, stringAndNumber} from "../../validators";
import {RangeDto} from "../../models/range-dto";
import { DateAdapter } from '@angular/material/core';

@Component({
  selector: 'app-patent-requests',
  templateUrl: './patent-requests.component.html',
  styleUrls: ['./patent-requests.component.scss']
})
export class PatentRequestsComponent {
  patents: PatentDto[] = [];
  type_options: SelectDto[] = [
    {name: "Podnosilac", value: "pod_ime"},
    {name: "Punomoćnik", value: "pun_ime"},
    {name: "Pronalazač", value: "pro_ime"},
    {name: "Izdvojena", value: "additional"},
    {name: "Dodatna", value: "child"},
    {name: "Prava prvenstva", value: "sibling"},
    {name: "Naslov", value: "naslov"},
    {name: "Datum prijave", value: "datum_prijave"},
    {name: "Broj prijave", value: "broj_prijave"}
  ]
  operator_options: SelectDto[] = [ {name: 'i', value: "i"}, {name:'ili', value:'ili'}, {name:'ne', value:"ne"}]

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

  filterFormGroup = this._formBuilder.group({
    type: ['', [Validators.required]],
    text: ['', [Validators.required]],
  })


  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });

  constructor(private patentService: PatentService,
              private router: Router, private _formBuilder: FormBuilder,
              public dialog: MatDialog,
              private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale('sr-Latn');
    this.patentService.getPatentList().subscribe(
      (res) => {
        console.log("--------------")
        console.log(res)
        let parser = new xml2js.Parser();
        parser.parseString(res, (err, result) => {
          console.log("**********************")
          console.log(result['List']['item'][0])
          this.patents = []
          for (let i of result['List']['item'])
            this.patents.push(i)
        });
      }
    )

  }

  getPDF($event: MouseEvent, id: string) {
    $event.stopPropagation();
    this.patentService.getPatentPDF(id).subscribe(
      (res) => {
        console.log("*************************************** pdf ***************************************")
        console.log(res)
        window.open(res, "_blank");
      }
    )
  }

  getHTML($event: MouseEvent, id: string) {
    $event.stopPropagation();
    this.patentService.getPatentHTML(id).subscribe(
      (res) => {
        console.log("*************************************** pdf ***************************************")
        console.log(res)
        window.open(res, "_blank");
      }
    )
  }

  openAddSolutionDialog($event: MouseEvent, idElement: string): void {
    $event.stopPropagation();
    let dialogRef
    dialogRef = this.dialog.open(AddSolutionComponent, {
      width: '30%',
      height: '50%',
      data: idElement
    })

    dialogRef.afterClosed().subscribe((res) => {
      console.log('The dialog was closed')
      if (res) {
        for (let r of this.patents) {
          if (r.id[0] === idElement) {
            r.hasSolution[0] = 'true'
          }
        }
      }
    })
  }

  openViewSolutionDialog($event: MouseEvent, idElement: string): void {
    $event.stopPropagation();
    let dialogRef
    dialogRef = this.dialog.open(ViewSolutionComponent, {
      width: '60%',
      height: '40%',
      data: idElement
    })

    dialogRef.afterClosed().subscribe((res) => {
      console.log('The dialog was closed')
    })
  }

  getURL(id: string) {
    this.router.navigateByUrl("patent/full/" + id[0].replace("/", "-"))
  }


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
    this.patentService.filteRequests(nextFilter).subscribe(
      (res) => {
        console.log("--------------")
        console.log(res)
        let parser = new xml2js.Parser();
        parser.parseString(res, (err, result) => {
          console.log("**********************")
          console.log(result['List']['item'][0])
          this.patents = []
          for (let i of result['List']['item'])
            this.patents.push(i)
        });
      }
    )
    console.log(this.lastFilter)
  }

  getRDF($event: MouseEvent, id: any) {
    $event.stopPropagation();
    this.patentService.getPatentRDF(id).subscribe(
      (res) => {
        console.log("*************************************** pdf ***************************************")
        console.log(res)
        window.open(res, "_blank");
      }
    )
  }

  getJSON($event: MouseEvent, id: any) {
    $event.stopPropagation();
    this.patentService.getPatentJSON(id).subscribe(
      (res) => {
        console.log("*************************************** pdf ***************************************")
        console.log(res)
        window.open(res, "_blank");
      }
    )
  }

  fruits: SearchBy[] = [];
  readonly separatorKeysCodes = [ENTER, COMMA, SPACE] as const;

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
    this.patentService.searchPatentList(this.fruits).subscribe(
      (res) => {
        this.handleResponse(res)
      }
    )
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


  @ViewChild("viewContainerRef", { read: ViewContainerRef }) vcr!: ViewContainerRef;
  refList: ComponentRef<FilterComponent>[] = []

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

  firstElement: FilterDto = {operator: "", type: "", value: ""}


  makeReport() {
    //alert("TODO: implement report")
    let range: RangeDto = {
      startDate: this.convertDateToString(this.range.controls.start.value?.toLocaleString()),
      endDate:  this.convertDateToString(this.range.controls.end.value?.toLocaleString())

    }
    alert(range.startDate);

    this.patentService.getReportForPeriod(range).subscribe(
      res => {
        window.open(res, "_blank");
      }
    )

  }

  private convertDateToString(param: string | undefined) {
    if (param === undefined)
      return ''
    let elems = param.split(",")
    let dateParts = elems[0].split("/")
    let day : string = dateParts[1]
    if (dateParts[1].length === 1)
      day = '0' + dateParts[1]
    let month : string = dateParts[0]
    if (dateParts[0].length === 1)
      month = '0' + dateParts[0]
    return day + "." + month +"." + dateParts[2] + "."

  }
}


/*

    this.lastFilter = {
      numberOperator: this.searchFormGroup.controls.numberOperator.value,
      number: this.searchFormGroup.controls.number.value,
      titleOperator: this.searchFormGroup.controls.titleOperator.value,
      title: this.searchFormGroup.controls.title.value,
      datumOperator: this.searchFormGroup.controls.datumOperator.value,
      datum: this.chosenDate,
      submitterOperator: this.searchFormGroup.controls.submitterOperator.value,
      submitter: this.searchFormGroup.controls.submitter.value,
      inventorOperator: this.searchFormGroup.controls.inventorOperator.value,
      inventor: this.searchFormGroup.controls.inventor.value,
      commissionerOperator: this.searchFormGroup.controls.commissionerOperator.value,
      commissioner: this.searchFormGroup.controls.commissioner.value,
    }
 */
