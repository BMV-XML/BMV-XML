import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {MatRadioChange} from "@angular/material/radio";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PatentService} from "../../services/patent.service";
import {AddSolutionDto} from "../../../shared/models/add-solution-dto";
import * as xml2js from "xml2js";

@Component({
  selector: 'app-add-solution',
  templateUrl: './add-solution.component.html',
  styleUrls: ['./add-solution.component.scss']
})
export class AddSolutionComponent {

  state: boolean = true;
  isDisabled: boolean = false;
  reason: FormControl = new FormControl('', []);
  solutionGroup: FormGroup;
  error: string = '';

  constructor(
    private patentService: PatentService,
    public dialog: MatDialog,
    public dialogRef: MatDialogRef<AddSolutionComponent>,
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
    console.log("--------------------------------------- add solution disabled --------------")
    console.log(this.state)
    console.log(this.reason.value)
    if (this.state || this.reason.value !== '')
      this.isDisabled = false
    else
      this.isDisabled = true
  }

  submit() {
    console.log("------------------ submit solution --------------")
    let solution : AddSolutionDto = {approved: this.state, rejectionText: this.reason.value, requestId: this.requestId}

    this.patentService.addSolution(solution).subscribe(
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
