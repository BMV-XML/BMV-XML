import {Component, Inject} from '@angular/core';
import {SolutionDto} from "../../../shared/models/solution-dto";
import {PatentService} from "../../../patent/services/patent.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import * as xml2js from "xml2js";
import {StampService} from "../../services/stamp-service/stamp.service";

@Component({
  selector: 'app-view-stamp-solution',
  templateUrl: './view-stamp-solution.component.html',
  styleUrls: ['./view-stamp-solution.component.scss']
})
export class ViewStampSolutionComponent {
  solution: SolutionDto = {
    approved: [],
    date: [],
    officialName: [],
    officialSurname: [],
    rejectionText: [],
    requestId: []
  }


  constructor(
      private stampService: StampService,
      public dialog: MatDialog,
      public dialogRef: MatDialogRef<ViewStampSolutionComponent>,
      @Inject(MAT_DIALOG_DATA) public requestId: string) {
    console.log(requestId)
    this.stampService.getSolution(requestId).subscribe(
        (res) => {
          console.log("**************** view solution **************")
          console.log(res)
          let parser = new xml2js.Parser();
          parser.parseString(res, (err, result) => {
            //Extract the value from the data element
            // extractedData = result['config']['data']
            console.log("**********************")
            this.solution = result['SolutionDTO']
            console.log(result)
          });
        }
    )
  }

}
