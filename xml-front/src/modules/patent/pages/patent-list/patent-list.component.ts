import { Component } from '@angular/core';
import {MessageService} from "primeng/api";
import {PatentService} from "../../services/patent.service";
import {Router} from "@angular/router";
import {FormBuilder} from "@angular/forms";
import {MatDialog} from "@angular/material/dialog";
import {FilterDto} from "../../models/filter-dto";
import {SearchBy} from "../../models/search-by";
import * as xml2js from "xml2js";
import {PatentDto} from "../../models/patent-dto";

@Component({
  selector: 'app-patent-list',
  templateUrl: './patent-list.component.html',
  styleUrls: ['./patent-list.component.scss'],
  providers: [MessageService]
})
export class PatentListComponent {

  patents: PatentDto[] = [];
  searchOrFiltered: boolean = false;
  searched: boolean = true;
  searchParams: SearchBy[] =[];
  filterParams: FilterDto[] = [];

  constructor(private patentService: PatentService,
              private router: Router, private _formBuilder: FormBuilder,
              public dialog: MatDialog,
              private readonly messageService: MessageService){
    this.patentService.getPatentListUser().subscribe(
      (res) => {
        this.handleResponse(res);
      }
    )
  }

  filter($event: FilterDto[]) {
    this.patentService.filteRequestsUser($event).subscribe(
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
    this.patentService.searchPatentListUser($event).subscribe(
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

  getURL(id: string) {
    this.router.navigateByUrl("patent/full/" + id[0].replace("/", "-"))

  }
}
