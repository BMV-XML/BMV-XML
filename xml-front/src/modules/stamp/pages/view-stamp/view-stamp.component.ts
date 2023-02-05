import {Component} from '@angular/core';
import * as xml2js from "xml2js";
import {StampService} from "../../services/stamp-service/stamp.service";
import {SimpleViewStampDto} from "../../models/simple-view-stamp-dto";
import {AddSolutionComponent} from "../../../patent/components/add-solution/add-solution.component";
import {AddStampSolutionComponent} from "../../components/add-stamp-solution/add-stamp-solution.component";
import {MatDialog} from "@angular/material/dialog";
import {DateAdapter} from "@angular/material/core";
import {Router} from "@angular/router";
import {ViewStampSolutionComponent} from "../../components/view-stamp-solution/view-stamp-solution.component";
import {SearchBy} from "../../../patent/models/search-by";
import {COMMA, ENTER, SPACE} from "@angular/cdk/keycodes";
import {MatChipEditedEvent, MatChipInputEvent} from "@angular/material/chips";

@Component({
    selector: 'app-view-stamp',
    templateUrl: './view-stamp.component.html',
    styleUrls: ['./view-stamp.component.scss']
})
export class ViewStampComponent {
    stamps: SimpleViewStampDto[] = [];


    constructor(private stampService: StampService,
                public dialog: MatDialog,
                private dateAdapter: DateAdapter<Date>,
                private router: Router) {
        this.stampService.getStampList().subscribe(
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

    getURL(id:string) {
        console.log(id);
        this.router.navigateByUrl("stamp/full/" + id[0].replace("/", "-"))
    }

    openAddSolutionDialog($event: MouseEvent, idElement: string) {
        $event.stopPropagation();
        let dialogRef
        dialogRef = this.dialog.open(AddStampSolutionComponent, {
            width: '30%',
            height: '50%',
            data: idElement
        })

        dialogRef.afterClosed().subscribe((res) => {
            console.log('The dialog was closed')
            if (res) {
                for (let r of this.stamps) {
                    if (r.id[0] === idElement) {
                        r.hasSolution[0] = Boolean('true')
                    }
                }
            }
        })

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



    fruits: SearchBy[] = [];
    readonly separatorKeysCodes = [ENTER, COMMA, SPACE] as const;

    add(event: MatChipInputEvent): void {
        const value = (event.value || '').trim();
        if (value) {
            this.fruits.push({name: value});
        }
        event.chipInput!.clear();
    }

    remove(fruit: SearchBy): void {
        const index = this.fruits.indexOf(fruit);

        if (index >= 0) {
            this.fruits.splice(index, 1);
        }
    }

    edit(fruit: SearchBy, event: MatChipEditedEvent) {
        const value = event.value.trim();
        if (!value) {
            this.remove(fruit);
            return;
        }
        const index = this.fruits.indexOf(fruit);
        if (index >= 0) {
            this.fruits[index].name = value;
        }
    }

    search() {
        this.stampService.searchStampList(this.fruits).subscribe(
            (res) => {
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
                this.stamps = []
                for (let i of result['List']['item'])
                    this.stamps.push(i)
            } catch (err) {
                this.stamps = []
            }
        });

    }
}
