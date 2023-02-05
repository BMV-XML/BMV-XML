import { Component, Inject } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { AuthorshipService } from 'modules/authorship/services/authorship.service';
import { ViewSolutionComponent } from 'modules/patent/components/view-solution/view-solution.component';
import { PatentService } from 'modules/patent/services/patent.service';
import * as xml2js from "xml2js";
import {SolutionDto} from "../../../shared/models/solution-dto";

@Component({
  selector: 'app-inspect-solution',
  templateUrl: './inspect-solution.component.html',
  styleUrls: ['./inspect-solution.component.scss']
})
export class InspectSolutionComponent {

  solution: SolutionDto = {
    approved: [],
    date: [],
    officialName: [],
    officialSurname: [],
    rejectionText: [],
    requestId: []
  }

  constructor(private authorshipService: AuthorshipService,
              public dialog: MatDialog,
              public dialogRef: MatDialogRef<InspectSolutionComponent>,
              @Inject(MAT_DIALOG_DATA) public requestId: string) {
    console.log(requestId)
    this.authorshipService.getSolution(requestId).subscribe(
      (res) => {
        let parser = new xml2js.Parser();
        parser.parseString(res, (err, result) => {
          this.solution = result['SolutionDTO']
          console.log(result)
        });
      }
    )
  }

}
