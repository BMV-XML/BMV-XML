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



  range = new FormGroup({
    start: new FormControl<Date | null>(null),
    end: new FormControl<Date | null>(null),
  });
  searchOrFiltered: boolean = false;
  searched: boolean = true;
  searchParams: SearchBy[] =[];
  filterParams: FilterDto[] = [];

  constructor(private patentService: PatentService,
              private router: Router, private _formBuilder: FormBuilder,
              public dialog: MatDialog,
              private readonly messageService: MessageService,
              private dateAdapter: DateAdapter<Date>) {
    this.dateAdapter.setLocale('sr-Latn');
    this.patentService.getPatentList().subscribe(
      (res) => {
        console.log("------------------------------- LIST ----------------------------------------")
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

  filter($event: FilterDto[]) {
    this.patentService.filteRequests($event).subscribe(
      (res) => {
        this.searchOrFiltered = true;
        this.searched = false;
        this.filterParams = $event;
        console.log("--------------")
        console.log(res)
        this.handleResponse(res)
      }
    )
  }

  search($event: SearchBy[]) {
    this.patentService.searchPatentList($event).subscribe(
      (res) => {
        this.searchOrFiltered = true;
        this.searched = true;
        this.searchParams = $event;
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
}

