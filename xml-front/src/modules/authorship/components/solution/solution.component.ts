import { Component, Inject } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { MatRadioChange } from '@angular/material/radio';
import { AuthorshipService } from 'modules/authorship/services/authorship.service';
import { AddSolutionComponent } from 'modules/patent/components/add-solution/add-solution.component';
import { AddSolutionDto } from 'modules/patent/models/add-solution-dto';
import * as xml2js from "xml2js";

@Component({
  selector: 'app-solution',
  templateUrl: './solution.component.html',
  styleUrls: ['./solution.component.scss']
})
export class SolutionComponent {

  state: boolean = true;
  isDisabled: boolean = false;
  reason: FormControl = new FormControl('', []);
  solutionGroup: FormGroup;
  error: string = '';

  constructor(
    private authorshipService: AuthorshipService,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<SolutionComponent>,
    @Inject(MAT_DIALOG_DATA) public requestId: string) {
    this.solutionGroup = new FormGroup({
      reason: this.reason
    })
  }

  optionChanged($event: MatRadioChange) {
    if ($event.value === '1')
      this.state = true
    else
      this.state = false
    this.handleInput()
  }

  handleInput() {
    if (this.state || this.reason.value !== '')
      this.isDisabled = false
    else
      this.isDisabled = true
  }

  submit() {
    console.log("------------------ submit solution --------------")
    let solution : AddSolutionDto = {approved: this.state, rejectionText: this.reason.value, requestId: this.requestId}

    this.authorshipService.addSolution(solution).subscribe(
      (res) =>{
        console.log("------------------- add solution ---------------")
        console.log(res)
        var parser = new xml2js.Parser();
        parser.parseString(res, (err, result) => {
          if (result['Boolean'][0])
            this.dialogRef.close(res);
        })
      }

    )
  }

}
