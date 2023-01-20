import { Component } from '@angular/core';
import {PatentService} from "../../services/patent.service";
import * as xml2js from "xml2js";
import {PatentDto} from "../../models/patent-dto";
import {Router} from "@angular/router";

@Component({
  selector: 'app-patent-requests',
  templateUrl: './patent-requests.component.html',
  styleUrls: ['./patent-requests.component.scss']
})
export class PatentRequestsComponent {
  patents: PatentDto[] = [];

  constructor(private patentService : PatentService,
              private router: Router) {
    this.patentService.getPatentList().subscribe(
      (res) => {
        console.log("--------------")
        console.log(res)
        let parser = new xml2js.Parser();
        parser.parseString(res, (err,result) =>{
          //Extract the value from the data element
          // extractedData = result['config']['data']
          console.log("**********************")
          console.log(result['List']['item'][0])
          this.patents = []
          for (let i of result['List']['item'])
            this.patents.push(i)
          //this.patents = result['List'];
        });
      }
    )

  }

  getPDF() {
    let id = "P-19121157-23"
    this.patentService.getPatentPDF(id).subscribe(
      (res)=>{
        console.log("*************************************** pdf ***************************************")
        console.log(res)
        window.open(res, "_blank");
        //this.router.navigateByUrl(res);
      }
    )
  }

  getHTML() {
    let id = "P-19121157-23"
    this.patentService.getPatentHTML(id).subscribe(
      (res)=>{
        console.log("*************************************** pdf ***************************************")
        console.log(res)
        window.open(res, "_blank");
        //this.router.navigateByUrl(res);
      }
    )
  }
}
