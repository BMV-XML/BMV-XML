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
import {MessageService} from "primeng/api";
import {start} from "@popperjs/core";

@Component({
  selector: 'app-patent-requests',
  templateUrl: './patent-requests.component.html',
  styleUrls: ['./patent-requests.component.scss'],
  providers: [MessageService]
})
export class PatentRequestsComponent {
  patents: PatentDto[] = [];
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
              private readonly messageService: MessageService,
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
          this.patents = []
          try{
          console.log(result['List']['item'][0])
          for (let i of result['List']['item'])
            this.patents.push(i)
          }catch {
            this.messageService.add({
              key: 'patent-list-message',
              severity: 'warn',
              summary: 'Neuspešno filtriranje',
              detail: 'Nema rezultata.'
            })
          }
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
    console.log("************** report ***************")
    console.log(this.patentService.convertDateToStringInReport(this.range.controls.start.value?.toLocaleString()))
    console.log(this.range.controls.end.value)
    console.log(new Date())
    if (this.range.controls.start.value == null){
      console.log("START NULL")
      this.messageService.add({
        key: 'patent-list-message',
        severity: 'warn',
        summary: 'Neuspešna pretraga',
        detail: 'Početni datum mora da postoji.'
      })
      return
    }else if (this.range.controls.end.value == null){
      console.log("END NULL")
      this.messageService.add({
        key: 'patent-list-message',
        severity: 'warn',
        summary: 'Neuspešna pretraga',
        detail: 'Krajnji datum mora da postoji.'
      })
      return
    }else if (this.range.controls.start.value > new Date || this.range.controls.end.value > new Date){
      console.log("Ne VALJA")
      this.messageService.add({
        key: 'patent-list-message',
        severity: 'warn',
        summary: 'Neuspešna pretraga',
        detail: 'Datumi moraju biti u prošlosti.'
      })
      return
    }

    let range: RangeDto = {
      startDate: this.patentService.convertDateToStringInReport(this.range.controls.start.value?.toLocaleString()),
      endDate:  this.patentService.convertDateToStringInReport(this.range.controls.end.value?.toLocaleString())
    }

    this.messageService.add({
      key: 'patent-list-message',
      severity: 'success',
      summary: 'Uspešno izveštavanje',
      detail: 'Sačekajte malo za preuzimanje.'
    })

    this.patentService.getReportForPeriod(range).subscribe(
      res => {
        window.open(res, "_blank");
      }
    )

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
