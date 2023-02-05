import {Component} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {ActivatedRoute, Router} from "@angular/router";
import {StampService} from "../../services/stamp-service/stamp.service";
import * as xml2js from "xml2js";
import {FullStampDto} from "../../models/full-stamp-dto";
import {AddStampSolutionComponent} from "../../components/add-stamp-solution/add-stamp-solution.component";
import {ViewStampSolutionComponent} from "../../components/view-stamp-solution/view-stamp-solution.component";

@Component({
    selector: 'app-full-stamp',
    templateUrl: './full-stamp.component.html',
    styleUrls: ['./full-stamp.component.scss']
})
export class FullStampComponent {
    requestId: string = '';
    stamp:any


    constructor(private stampService: StampService, public dialog: MatDialog,
                private route: ActivatedRoute, private router: Router) {
    }

    ngOnInit() {
        this.route.params.subscribe(params => {
            this.requestId = params['id']
            this.update()
        });

    }

    private update() {
        this.stampService.getStamp(this.requestId).subscribe(
            (res: string) => {
                console.log("******** RES *******")
                console.log(res)
                let parser = new xml2js.Parser();
                parser.parseString(res, (err, result) => {
                    //Extract the value from the data element
                    // extractedData = result['config']['data']
                    console.log("**********************");
                    console.log(result);
                    this.stamp = result['FullStampDTO']
                    this.requestId = this.stamp.id[0];
                    console.log("-------------   objekat    -------------")
                    console.log(this.stamp)
                    console.log(" +++++++++++++  applicants  ++++++++++++++")
                    console.log(this.stamp.applicants[0].applicants)
                });
            })
    }


    openAddSolutionDialog($event: MouseEvent) {
        $event.stopPropagation();
        let dialogRef
        dialogRef = this.dialog.open(AddStampSolutionComponent, {
            width: '30%',
            height: '50%',
            data: this.requestId
        })

        dialogRef.afterClosed().subscribe((res) => {
            console.log('The dialog was closed')
            if (res) {
                this.update();
            }
        })

    }

    openViewStampSolutionDialog($event: MouseEvent) {
        $event.stopPropagation();
        let dialogRef
        dialogRef = this.dialog.open(ViewStampSolutionComponent, {
            width: '60%',
            height: '40%',
            data: this.requestId
        })

        dialogRef.afterClosed().subscribe((res) => {
            console.log('The dialog was closed')
        })
    }

}
