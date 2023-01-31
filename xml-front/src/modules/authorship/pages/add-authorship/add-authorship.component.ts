import { Component, ComponentRef, ViewChild, ViewContainerRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthorshipService } from 'modules/authorship/services/authorship.service';
import { EntityDto } from 'modules/patent/models/entity-dto';
import { capitalFirstLetterTextWithSpace, postalNumber, stringAndNumber } from 'modules/patent/validators';
import { MessageService } from 'primeng/api';
import { AuthorshipRequestDto, AuthorsWorkDto } from 'modules/authorship/models/authtorship-request-dto';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { AuthorComponent } from 'modules/authorship/components/author/author.component';

@Component({
  selector: 'app-add-authorship',
  templateUrl: './add-authorship.component.html',
  styleUrls: ['./add-authorship.component.scss'],
  providers: [MessageService]
})
export class AddAuthorshipComponent {

  isLinear: boolean = true;

  deliveryFormGroup = this._formBuilder.group({
    street: ['', [Validators.required, capitalFirstLetterTextWithSpace]],
    number: ['', [Validators.required, stringAndNumber]],
    postalNumber: ['', [Validators.required, postalNumber]],
    city: ['', [Validators.required, capitalFirstLetterTextWithSpace]],
    country: ['', [Validators.required, capitalFirstLetterTextWithSpace]],
  })

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
    type: ''
  }

  result : AuthorshipRequestDto = {
    authors: [],
    // address: {street: "", country: "", city: "", number: "", postalNumber: ""},
    // previousPatent: this.previousPatent,
    additionalPatent: true, separatedPatent: false,
    notifyMeViaEmails: false, notifyMeViaLetters: false,
    commissionerForJointRepresentation: false, commissionerForLetters: false, commissionerForRepresentation: false,
    inventorWantsToBeListed: false, submitterIsTheInventor: false,
    inventor: this.entity,
    commissioner: this.entity,
    submitter: this.entity,
    authorsWork: this.authorsWork,
    titles: ''
  }

  titlesXml: string = ''
  // priorityPatent: PreviousPatentDto[] = []

  // titles: TitleDto[] = []

  // USLOVI ZA PRELAZAK NA NAREDNO POLJE
  isSubmitterCompleted: boolean = true
  isCommissionerCompleted: boolean = false;
  isAuthorsWorkCompleted: boolean = false;
  isAuthorCompleted: boolean = true;

  isSubmitterTheAuthor: boolean = true;
  openCommissioner: boolean = false;
  isAuthorAnonymus: boolean = false;

  constructor(private _formBuilder: FormBuilder,
              private authroshipService: AuthorshipService,
              private readonly messageService: MessageService) {

    }

  @ViewChild("authorContainerRef", {read: ViewContainerRef}) vcr!: ViewContainerRef;
  ref!: ComponentRef<AuthorComponent>
  counter: number = 0

  isApplicantCompleted(): boolean {
    return true;
  }

  submitterEvent($event: any) {
    this.isSubmitterCompleted = $event.completed;
    console.log(this.isSubmitterCompleted)
    this.result.submitter = $event
  }

  commissionerEvent($event: any) {
    this.isCommissionerCompleted = $event.completed;
    console.log(this.isCommissionerCompleted)
    this.result.commissioner = $event
  }

  authorsWorkEvent($event: any) {
    this.isAuthorsWorkCompleted = $event.completed;
    console.log(this.isSubmitterCompleted)
    this.result.authorsWork = $event
  }

  openCommissionerFields($event: any) {
    this.openCommissioner = $event.checked;
  }

  // completeSubmitter() {
  //   this.isSubmitterCompleted = true;
  // }

  // completeAuthorsWork() {
  //   this.isAuthorsWorkCompleted = true;
  // }

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
    console.log("id od applikanta")
    console.log(this.ref.instance.author.id)
    this.ref.instance.previousApplicant.subscribe(
      value => {
        if (value.completed)
            console.log(this.counter)
        this.result.authors[value.id] = value
        console.log("------------------ stanje ------------- ")
        console.log("counnter: ")
        console.log(this.counter)
        console.log(this.result)
      }
    );
  }

  removeAuthor() {
    const index = this.vcr.indexOf(this.ref.hostView)
    if (index != -1) this.vcr.remove(index)
    if (this.result.authors.length === this.counter) {
        this.counter--
        this.result.authors.pop()
    }
    console.log("------------------ stanje ------------- ")
    console.log(this.result)
  }

}
