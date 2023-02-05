import { Component } from '@angular/core';
import {SimpleViewStampDto} from "../../models/simple-view-stamp-dto";
import {StampService} from "../../services/stamp-service/stamp.service";
import {MatDialog} from "@angular/material/dialog";
import {DateAdapter} from "@angular/material/core";
import {Router} from "@angular/router";
import * as xml2js from "xml2js";
import {ViewStampSolutionComponent} from "../../components/view-stamp-solution/view-stamp-solution.component";

@Component({
  selector: 'app-view-accepted-request',
  templateUrl: './view-accepted-request.component.html',
  styleUrls: ['./view-accepted-request.component.scss']
})
export class ViewAcceptedRequestComponent {
  stamps: SimpleViewStampDto[] = [];


  constructor(private stampService: StampService,
              public dialog: MatDialog,
              private dateAdapter: DateAdapter<Date>,
              private router: Router) {
    this.stampService.getAcceptedStampList().subscribe(
        (res) => {
          console.log("--------------")
          console.log(res)
          let parser = new xml2js.Parser();
          parser.parseString(res, (err, result) => {
            console.log("after parse")
            console.log(result)
            console.log("**********************")
            console.log(result['List']['item'][0])
            this.stamps = []
            for (let i of result['List']['item'])
              this.stamps.push(i)
            console.log(this.stamps)
          });
        }
    )
  }

  getHTML($event: MouseEvent, id: string) {
    $event.stopPropagation();
    this.stampService.getStampHTML(id).subscribe(
        (res) => {
          console.log("*************************************** html ***************************************")
          console.log(res)
          window.open(res, "_blank");
        }
    )
  }

  getPDF($event: MouseEvent, id: string) {
    $event.stopPropagation();
    this.stampService.getStampPDF(id).subscribe(
        (res) => {
          console.log("*************************************** pdf ***************************************")
          console.log(res)
          window.open(res, "_blank");
        }
    )

  }

  getRDF($event: MouseEvent, id: any) {
    $event.stopPropagation();
    this.stampService.getStampRDF(id).subscribe(
        (res) => {
          console.log("*************************************** rdf ***************************************")
          console.log(res)
          window.open(res, "_blank");
        }
    )
  }

  getJSON($event: MouseEvent, id: any) {
    $event.stopPropagation();
    this.stampService.getStampJSON(id).subscribe(
        (res) => {
          console.log("*************************************** json ***************************************")
          console.log(res)

          window.open(res.replace("?", "Ž"), "_blank");
        }
    )
  }


  openViewStampSolutionDialog($event: MouseEvent, idElement: string) {
    $event.stopPropagation();
    let dialogRef
    dialogRef = this.dialog.open(ViewStampSolutionComponent, {
      width: '60%',
      height: '40%',
      data: idElement
    })

    dialogRef.afterClosed().subscribe((res) => {
      console.log('The dialog was closed')
    })
  }


    getURL(id:string) {
        console.log(id);
        this.router.navigateByUrl("stamp/full/" + id[0].replace("/", "-"))
    }


  private handleResponse(res: string) {
    console.log("--------------")
    console.log(res)
    let parser = new xml2js.Parser();
    parser.parseString(res, (err, result) => {
      try {
        console.log("**********************")
        console.log(result['List']['item'][0])
        this.stamps = []
        for (let i of result['List']['item'])
          this.stamps.push(i)
      } catch (err) {
        this.stamps = []
      }
    });

  }

}
