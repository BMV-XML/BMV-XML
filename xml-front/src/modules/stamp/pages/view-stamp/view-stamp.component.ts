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
import {FormControl, FormGroup} from "@angular/forms";
import {MessageService} from "primeng/api";
import {RangeDto} from "../../../patent/models/range-dto";

@Component({
    selector: 'app-view-stamp',
    templateUrl: './view-stamp.component.html',
    styleUrls: ['./view-stamp.component.scss'],
    providers:[MessageService]
})
export class ViewStampComponent {
    stamps: SimpleViewStampDto[] = [];

    range = new FormGroup({
        start: new FormControl<Date | null>(null),
        end: new FormControl<Date | null>(null),
    });

    constructor(private stampService: StampService,
                public dialog: MatDialog,
                private dateAdapter: DateAdapter<Date>,
                private router: Router,
                private messageService: MessageService) {
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

    getURL(id: string) {
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


    makeReport() {
        console.log("************** report ***************")
        console.log(this.stampService.convertDateToStringInReport(this.range.controls.start.value?.toLocaleString()))
        console.log(this.range.controls.end.value)
        console.log(new Date())
        if (this.range.controls.start.value == null) {
            console.log("START NULL")
            this.messageService.add({
                key: 'stamp-list-message',
                severity: 'warn',
                summary: 'Neuspešna pretraga',
                detail: 'Početni datum mora da postoji.'
            })
            return
        } else if (this.range.controls.end.value == null) {
            console.log("END NULL")
            this.messageService.add({
                key: 'stamp-list-message',
                severity: 'warn',
                summary: 'Neuspešna pretraga',
                detail: 'Krajnji datum mora da postoji.'
            })
            return
        } else if (this.range.controls.start.value > new Date || this.range.controls.end.value > new Date) {
            console.log("Ne VALJA")
            this.messageService.add({
                key: 'stamp-list-message',
                severity: 'warn',
                summary: 'Neuspešna pretraga',
                detail: 'Datumi moraju biti u prošlosti.'
            })
            return
        }

        let range: RangeDto = {
            startDate: this.stampService.convertDateToStringInReport(this.range.controls.start.value?.toLocaleString()),
            endDate:  this.stampService.convertDateToStringInReport(this.range.controls.end.value?.toLocaleString())
        }

        this.messageService.add({
            key: 'stamp-list-message',
            severity: 'success',
            summary: 'Uspešno izveštavanje',
            detail: 'Sačekajte malo za preuzimanje.'
        })

        this.stampService.getReportForPeriod(range).subscribe(
            res => {
                console.log(res);
                window.open(res, "_blank");
            }
        )
    }
}
