import {Component, ComponentRef, ViewChild, ViewContainerRef} from '@angular/core';
import {AbstractControl, FormBuilder, ValidatorFn, Validators} from "@angular/forms";
import {XonomyService} from "../../services/xonomy.service";
import * as xml2js from "xml2js";
import * as _ from "lodash";
import {TitleDto} from "../../models/title-dto";
import {EntityDto} from "../../models/entity-dto";
import {PatentRequestDto} from "../../models/patent-request-dto";
import {MatCheckboxChange} from "@angular/material/checkbox";
import {PreviousPatentDto} from "../../models/previous-patent-dto";
import {MatRadioChange} from "@angular/material/radio";
import {PatentComponent} from "../../components/patent/patent.component";
import {capitalFirstLetterTextWithSpace, postalNumber, stringAndNumber} from "../../validators";
import {PatentService} from "../../services/patent.service";
import {MessageService} from "primeng/api";

declare const Xonomy: any

@Component({
  selector: 'app-add-patent',
  templateUrl: './add-patent.component.html',
  styleUrls: ['./add-patent.component.scss'],
  providers: [MessageService]
})
export class AddPatentComponent {

  isLinear: boolean = true;

  deliveryFormGroup = this._formBuilder.group({
    street: ['', [Validators.required, capitalFirstLetterTextWithSpace]],
    number: ['', [Validators.required, stringAndNumber]],
    postalNumber: ['', [Validators.required, postalNumber]],
    city: ['', [Validators.required, capitalFirstLetterTextWithSpace]],
    country: ['', [Validators.required, capitalFirstLetterTextWithSpace]],
  })

  entity: EntityDto = {
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
  previousPatent: PreviousPatentDto = {applicationNumber: "", submissionDate: null, country: "",completed: false,id: -1}
  result : PatentRequestDto = {
    priorityPatent: "", titles: "",
    address: {street: "", country: "", city: "", number: "", postalNumber: ""},
    previousPatent: this.previousPatent,
    additionalPatent: true, separatedPatent: false,
    notifyMeViaEmails: false, notifyMeViaLetters: false,
    commissionerForJointRepresentation: false, commissionerForLetters: false, commissionerForRepresentation: false,
    inventorWantsToBeListed: false,submitterIsTheInventor: false,
    inventor: this.entity,
    commissioner: this.entity,
    submitter: this.entity
  }

  priorityPatent: PreviousPatentDto[] = []

  titles: TitleDto[] = []

  isSubmitterCompleted: boolean = false
  isCommissionerCompleted: boolean = false;
  isInventorCompleted: boolean = false;
  isSubmitterTheInventor: boolean = false;
  showAdditionalPatentForm: boolean = true;
  isAdditionalPatentCompleted: boolean = false;


  constructor(private _formBuilder: FormBuilder, private xonomyService: XonomyService,
              private patentService: PatentService,
              private readonly messageService: MessageService) {}

  ngAfterViewInit(): void {
    let xml = "<Naslovi><Naslov jezik='SR'>aaaa</Naslov><Naslov jezik='HU'>XXXX</Naslov></Naslovi>";
    let editor = document.querySelector("#editor");
    let specification = this.xonomyService.patentSpecification;
    Xonomy.render(xml, editor, specification);
    Xonomy.setMode("laic")
  }

  completed() {
    let parser = new xml2js.Parser();
    try {
      parser.parseString(Xonomy.harvest(), (err, result) => {
        //Extract the value from the data element
        // extractedData = result['config']['data']
        //console.log("******************************************************")
        //console.log(result["Naslovi"]["Naslov"])
        let naslovi = result["Naslovi"]["Naslov"]
        //this.given_naslovi = []
        this.titles = []
        for (let n of naslovi) {
          //console.log(n)
          //this.given_naslovi.push({
          this.titles.push({
            language: n["$"]["jezik"],
            title: n["_"]
          })
        }
        //console.log(this.given_naslovi)
        //console.log("++++++++++++++++++++++++++++++++++++++++++++++++++++++")
        return this.checkIfGivenTitlesAreValid()

      });
      return this.checkIfGivenTitlesAreValid()
    } catch (err) {
      return false;
    }
  }

  checkIfGivenTitlesAreValid() {
    if (this.titles.length < 1)
      return false;
    let containsSR = false;
    for (let n of this.titles){
      if (n.language === 'SR')
        containsSR = true
      if (n.title === '')
        return false
      if (n.language === '')
        return false
    }
    if (containsSR)
      return true
    return false
  }

  submitterEvent($event: EntityDto) {
    this.isSubmitterCompleted = $event.completed;
    console.log(this.isSubmitterCompleted)
    this.result.submitter = $event
  }


  commissionerEvent($event: EntityDto) {
    this.isCommissionerCompleted = $event.completed;
    this.result.commissioner = $event
  }

  submitterIsInventorChange($event: MatCheckboxChange) {
    if ($event.checked)
      this.isInventorCompleted = true
    this.isSubmitterTheInventor = $event.checked;
    this.result.submitterIsTheInventor = $event.checked
  }

  inventorEvent($event: EntityDto) {
    this.isInventorCompleted = $event.completed;
    this.result.inventor = $event
    this.result.submitterIsTheInventor = false
  }

  inventorWantsToBeListed($event: MatCheckboxChange) {
    this.result.inventorWantsToBeListed = $event.checked
  }

  commissionerForRepresentationChange($event: MatCheckboxChange) {
    this.result.commissionerForRepresentation = $event.checked
  }

  commissionerForLettersChange($event: MatCheckboxChange) {
    this.result.commissionerForLetters = $event.checked
  }

  commissionerForJointRepresentationChange($event: MatCheckboxChange) {
    this.result.commissionerForJointRepresentation = $event.checked
  }

  notifyMeWithLettersChange($event: MatCheckboxChange) {
    this.result.notifyMeViaLetters = $event.checked
  }

  notifyMeViaEmailChange($event: MatCheckboxChange) {
    this.result.notifyMeViaEmails = $event.checked
  }

  updatePatent($event: PreviousPatentDto) {
    this.result.previousPatent = $event
    this.isAdditionalPatentCompleted = $event.completed
  }

  additionalPatentOptionChanged($event: MatRadioChange) {
    if ($event.value === '1'){
      this.result.additionalPatent = true
      this.result.separatedPatent = false
      if (!this.result.previousPatent?.completed)
        this.isAdditionalPatentCompleted = false
    }
    else if ($event.value === '2'){
      this.result.additionalPatent = false
      this.result.separatedPatent = true
      if (!this.result.previousPatent?.completed)
        this.isAdditionalPatentCompleted = false
    }else{
      this.result.additionalPatent = false
      this.result.separatedPatent = false
      this.isAdditionalPatentCompleted = true
      this.result.previousPatent = this.previousPatent
    }
    this.showAdditionalPatentForm = this.result.separatedPatent || this.result.additionalPatent
  }

  @ViewChild("viewContainerRef", { read: ViewContainerRef }) vcr!: ViewContainerRef;
  ref!: ComponentRef<PatentComponent>
  counter: number = 0

  addPatent() {
    this.ref = this.vcr.createComponent(PatentComponent)
    //this.ref.instance.id = this.counter
    this.ref.instance.patent.id = this.counter
    this.counter++
    this.ref.instance.previousPatent.subscribe(
      value =>{
        if (value.completed)
          this.priorityPatent[value.id] = value

        console.log("------------------ stanje ------------- ")
        console.log(this.priorityPatent)
      }
    );
  }

  removeChild() {
    const index = this.vcr.indexOf(this.ref.hostView)
    if (index != -1) this.vcr.remove(index)
    if (this.priorityPatent.length === this.counter){
      this.counter--
      this.priorityPatent.pop()
    }
    console.log("------------------ stanje ------------- ")
    console.log(this.priorityPatent)
  }

  isPriorityCompleted(){
    if (!this.isAdditionalPatentCompleted)
      return false
    if (this.priorityPatent.length == this.counter)
      return true
    return false
  }

  nextisSubmitterCompleted() {
    this.isSubmitterCompleted = true
  }

  nextisCommissionerCompleted() {
    this.isCommissionerCompleted = true
  }

  submitRequest() {
    if (this.result.commissionerForLetters && this.result.commissionerForRepresentation){
      this.messageService.add({ key: 'request-message', severity: 'info', summary: 'Imate problem sa zahtevom', detail: 'Punomoćnik ne može paralelno da bude i za pisma i predstavnik'})
      return;
    }

    this.result.address = {
      city: this.deliveryFormGroup.controls["city"].value,
      country: this.deliveryFormGroup.controls["country"].value,
      number: this.deliveryFormGroup.controls["number"].value,
      postalNumber: this.deliveryFormGroup.controls["postalNumber"].value,
      street: this.deliveryFormGroup.controls["street"].value,
    }
    this.patentService.submitRequest(this.result, this.titles, this.priorityPatent).subscribe(
      (res) => {
        console.log("```````````````````` RESULT ``````````````````````")
        console.log(res)
      }
    )
  }
}

