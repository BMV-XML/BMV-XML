import { Component, ComponentRef, ViewChild, ViewContainerRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthorshipService } from 'modules/authorship/services/authorship.service';
import { EntityDto } from 'modules/patent/models/entity-dto';
import { capitalFirstLetterTextWithSpace, postalNumber, stringAndNumber } from 'modules/patent/validators';
import { MessageService } from 'primeng/api';
import { AttachmentsDto, AuthorshipRequestDto, AuthorsWorkDto } from 'modules/authorship/models/authtorship-request-dto';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { AuthorComponent } from 'modules/authorship/components/author/author.component';
import { AuthorDto } from 'modules/authorship/models/author';
import * as xml2js from "xml2js";

@Component({
  selector: 'app-add-authorship',
  templateUrl: './add-authorship.component.html',
  styleUrls: ['./add-authorship.component.scss'],
  providers: [MessageService]
})
export class AddAuthorshipComponent {

  isLinear: boolean = true;

  entity: EntityDto = {
    id:0,
    businessName: "",
    citizenship: "",
    city: "",
    completed: false,
    country: "",
    email: "",
    fax: "",
    name: "",
    number: "",
    person: false,
    phone: "",
    postalNumber: "",
    street: "",
    surname: ""
  }
  
  authorsWork: AuthorsWorkDto = {
    title: '',
    alternateTitle: '',
    isRemade: false,
    remadeTitle: '',
    name: '',
    surname: '',
    wayOfUsage: '',
    form: '',
    type: '',
    madeInWorkRelationship: false,
    completed: false
  }

  result : AuthorshipRequestDto = {
    submitter: null,
    commissioner: null,
    authors: "",
    submitterIsAuthor: true,
    authorsWork: null,
    anonymusAuthor: false,
    attachments: null
  }

  authors: AuthorDto[] = []

  attachments: AttachmentsDto = {
    descriptionPath: "",
    examplePath: ""
  }

  // USLOVI ZA PRELAZAK NA NAREDNO POLJE
  isSubmitterCompleted: boolean = true  // false
  isCommissionerCompleted: boolean = true;
  isAuthorsWorkCompleted: boolean = true; // false

  openCommissioner: boolean = false;
  isSubmitterTheAuthor: boolean = true;
  isAuthorAnonymus: boolean = true;
  autorCompleted: boolean = false;

  constructor(private _formBuilder: FormBuilder,
              private authorshipService: AuthorshipService,
              private readonly messageService: MessageService) {

  }

  @ViewChild("authorContainerRef", {read: ViewContainerRef}) vcr!: ViewContainerRef;
  ref!: ComponentRef<AuthorComponent>
  counter: number = 0

  submitterEvent($event: any) {
    this.isSubmitterCompleted = $event.completed;
    console.log(this.isSubmitterCompleted)
    this.result.submitter = $event
  }

  commissionerEvent($event: any) {
    if (this.openCommissioner) {
      this.isCommissionerCompleted = $event.completed;
      console.log(this.isCommissionerCompleted)
      this.result.commissioner = $event
    }
    else
      this.isCommissionerCompleted = true
  }

  authorsWorkEvent($event: any) {
    this.isAuthorsWorkCompleted = $event.completed;
    console.log(this.isAuthorsWorkCompleted)
    this.result.authorsWork = $event
  }

  openCommissionerFields($event: any) {
    this.openCommissioner = $event.checked;
    this.isCommissionerCompleted = !$event.checked;
  }

  isAuthorCompleted(): boolean {
    if (this.isSubmitterTheAuthor)
      return true;
    if (this.isAuthorAnonymus)
      return true;
    
    this.ref.instance.validForm.subscribe(
      value => {
        this.autorCompleted = value
      }
    )

    return this.autorCompleted;
  }

  submitterIsAuthorChange($event: MatCheckboxChange) {
    this.isSubmitterTheAuthor = $event.checked;
  }

  anonymusAuthor($event: MatCheckboxChange) {
    this.isAuthorAnonymus = $event.checked;
  }


  addAuthor() {
    this.ref = this.vcr.createComponent(AuthorComponent)
    this.ref.instance.author.id = this.counter
    this.ref.instance.id = this.counter
    this.counter++
    console.log("id od autora")
    console.log(this.ref.instance.author.id)
    this.ref.instance.previousApplicant.subscribe(
      value => {
        if (value.completed)
            console.log(this.counter)
        this.authors[value.id] = value
        console.log(this.authors)
      }
    );
  }

  removeAuthor() {
    const index = this.vcr.indexOf(this.ref.hostView)
    if (index != -1) this.vcr.remove(index)
    if (this.authors.length === this.counter) {
        this.counter--
        this.authors.pop()
    }
    console.log("------------------ stanje ------------- ")
    console.log(this.result)
  }

  selectExample(event: any) {
    const data = new FormData()
    data.append('files', event.target.files[0])
    this.authorshipService.setExampleFile(data).subscribe((res: any) => {
      var parser = new xml2js.Parser();
      parser.parseString(res, (err, result) => {
          this.attachments.examplePath = result['AttachmentsDTO']['examplePath'][0]
      })
    })
  }

  selectDescription(event: any) {
    const data = new FormData()
    data.append('files', event.target.files[0])
    this.authorshipService.setDescriptionFile(data).subscribe((res: any) => {
      var parser = new xml2js.Parser();
      parser.parseString(res, (err, result) => {
          this.attachments.descriptionPath = result['AttachmentsDTO']['descriptionPath'][0]
      })
    })
  }

  submitRequest() {
    this.result.attachments = this.attachments;
    this.authorshipService.submitRequest(this.result, this.authors).subscribe(
      (res: string) => {
        console.log("```````````````````` RESULT ``````````````````````")
        console.log(res)
      }
    )
  }

}
