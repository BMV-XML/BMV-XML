import { Component } from '@angular/core';
import {PatentService} from "../../services/patent.service";
import {ActivatedRoute, Router} from "@angular/router";
import * as xml2js from "xml2js";
import {FullPatentDto} from "../../models/full-patent-dto";
import {AddSolutionComponent} from "../../components/add-solution/add-solution.component";
import {ViewSolutionComponent} from "../../components/view-solution/view-solution.component";
import {MatDialog} from "@angular/material/dialog";

class FullPatentDTO {
}

@Component({
  selector: 'app-full-patent',
  templateUrl: './full-patent.component.html',
  styleUrls: ['./full-patent.component.scss']
})
export class FullPatentComponent {

  patent: FullPatentDto = {
    commissionerBusinessName: [],
    commissionerCitizenship: [],
    commissionerCity: [],
    commissionerCountry: [],
    commissionerEmail: [],
    commissionerFax: [],
    commissionerForReceivingLetters: [],
    commissionerForRepresentation: [],
    commissionerName: [],
    commissionerNumber: [],
    commissionerPerson: [],
    commissionerPhone: [],
    commissionerPostNumber: [],
    commissionerStreet: [],
    commissionerSurname: [],
    commissionercommonRepresentative: [],
    inventorBusinessName: [],
    inventorCitizenship: [],
    inventorCity: [],
    inventorCountry: [],
    inventorEmail: [],
    inventorFax: [],
    inventorName: [],
    inventorNumber: [],
    inventorPhone: [],
    inventorPostNumber: [],
    inventorStreet: [],
    inventorSurname: [],
    inventorsPerson: [],
    submitterBusinessName: [],
    submitterCitizenship: [],
    submitterCity: [],
    submitterCountry: [],
    submitterEmail: [],
    submitterFax: [],
    submitterIsTheInventor: [],
    submitterName: [],
    submitterNumber: [],
    submitterPerson: [],
    submitterPhone: [],
    submitterPostNumber: [],
    submitterStreet: [],
    submitterSurname: [],
    wantToBeListed: [],
    acknowladgedDateOfSubmission: [],
    additionalPatent: "",
    applicationDate: [],
    country: [],
    deliveryCity: [],
    deliveryCountry: [],
    deliveryNumber: [],
    deliveryPostNumber: [],
    deliveryStreet: [],
    emailNotification: [],
    hasSolution: [],
    letterNotification: [],
    patentId: [],
    previousPatentId: '',
    priorityRights: [],
    recipientCity: [],
    recipientCountry: [],
    recipientName: [],
    recipientNumber: [],
    recipientPostNumber: [],
    recipientStreet: [],
    separatedPatent: [],
    solution: {
      date: [],
      requestId: [],
      officialName: [],
      officialSurname: [],
      rejectionText: [],
      approved: [],
    },
    submissionDate: [],
    titles: []
  };
  requestId : string = ''
  submitterPerson: boolean = false
  commissionerPerson: boolean = false
  inventorPerson: boolean = false

  constructor(private patentService: PatentService, public dialog: MatDialog,
  private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.route.params.subscribe( params => {
      this.requestId = params['id']
      this.updatePatent()
    });

  }

  generateUrl(applicationNumber: string) {
    this.router.navigateByUrl("patent/full/" + applicationNumber[0].replace("/", "-"))
  }

  openAddSolutionDialog($event: MouseEvent): void {
    $event.stopPropagation();
    let dialogRef
    dialogRef = this.dialog.open(AddSolutionComponent, {
      width: '30%',
      height: '50%',
      data: this.requestId
    })

    dialogRef.afterClosed().subscribe((res) => {
      console.log('The dialog was closed')
      if (res) {
        this.updatePatent();
      }
    })
  }

  openViewSolutionDialog($event: MouseEvent): void {
    $event.stopPropagation();
    let dialogRef
    dialogRef = this.dialog.open(ViewSolutionComponent, {
      width: '60%',
      height: '40%',
      data: this.requestId
    })

    dialogRef.afterClosed().subscribe((res) => {
      console.log('The dialog was closed')
    })
  }

  private updatePatent() {

    this.patentService.getPatent(this.requestId).subscribe(
      (res) => {
        console.log("******** RES *******")
        console.log(res)
        let parser = new xml2js.Parser();
        parser.parseString(res, (err, result) => {
          //Extract the value from the data element
          // extractedData = result['config']['data']
          console.log("**********************")
          this.patent = result['FullPatentDTO']
          this.submitterPerson = this.patent.submitterPerson[0] == 'true'
          this.commissionerPerson = this.patent.commissionerPerson[0] == 'true'
          console.log(this.patent.submitterName)

          console.log(result['FullPatentDTO'])
          this.patent.titles = result['FullPatentDTO']['titles']['0']['titles']
          try{
            this.patent.priorityRights = result['FullPatentDTO']['priorityRights']['0']['priorityRights']
          }catch {
            this.patent.priorityRights = []
          }
          if (this.patent.hasSolution[0] === 'true'){
            this.patent.solution = result['FullPatentDTO']['solution']['0']
          }
          console.log(this.patent)
          //this.patents = result['List'];
        });
      }
    )
  }
}
