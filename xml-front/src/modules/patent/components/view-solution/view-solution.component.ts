import {Component, Inject} from '@angular/core';
import {PatentService} from "../../services/patent.service";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {FormGroup} from "@angular/forms";
import {SolutionDto} from "../../../shared/models/solution-dto";
import * as xml2js from "xml2js";

@Component({
  selector: 'app-view-solution',
  templateUrl: './view-solution.component.html',
  styleUrls: ['./view-solution.component.scss']
})
export class ViewSolutionComponent {
  solution: SolutionDto = {
    approved: [],
    date: [],
    officialName: [],
    officialSurname: [],
    rejectionText: [],
    requestId: []
  }

  constructor(
    private patentService: PatentService,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<ViewSolutionComponent>,
    @Inject(MAT_DIALOG_DATA) public requestId: string) {
    console.log(requestId)
    this.patentService.getSolution(requestId).subscribe(
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
          //this.patents = result['List'];
        });
      }
    )
  }

}
