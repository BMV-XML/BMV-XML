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

declare const Xonomy: any

@Component({
  selector: 'app-add-patent',
  templateUrl: './add-patent.component.html',
  styleUrls: ['./add-patent.component.scss']
})
export class AddPatentComponent {

  isLinear: boolean = true;

  deliveryFormGroup = this._formBuilder.group({
    street: ['', Validators.required],
    number: ['', Validators.required],
    postalNumber: ['', Validators.required],
    city: ['', Validators.required],
    country: ['', Validators.required],
  })

  result : PatentRequestDto = {
    titles: [],
    priorityPatent: [],
    address: undefined, previousPatent: undefined,
    additionalPatent: true, separatedPatent: false,
    notifyMeViaEmails: false, notifyMeViaLetters: false,
    commissionerForJointRepresentation: false, commissionerForLetters: false, commissionerForRepresentation: false,
    inventorWantsToBeListed: false,
    inventor: undefined, submitterIsTheInventor: false, commissioner: undefined, submitter: undefined}

  titles: string = ''
  isSubmitterCompleted: boolean = false
  isCommissionerCompleted: boolean = false;
  isInventorCompleted: boolean = false;
  isSubmitterTheInventor: boolean = false;
  //given_naslovi: TitleDto[] = [];
  additionalPatent: boolean = true;
  isAdditionalPatentCompleted: boolean = false;


  constructor(private _formBuilder: FormBuilder, private xonomyService: XonomyService) {
  }


  ngAfterViewInit(): void {
    let xml = "<Naslovi><Naslov jezik='SR'>aaaa</Naslov><Naslov jezik='HU'>XXXX</Naslov></Naslovi>";
    let editor = document.querySelector("#editor");
    let specification = this.xonomyService.patentSpecification;
    Xonomy.render(xml, editor, specification);
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
        this.result.titles = []
        for (let n of naslovi) {
          //console.log(n)
          //this.given_naslovi.push({
          this.result.titles.push({
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
    if (this.result.titles.length < 1)
      return false;
    let containsSR = false;
    for (let n of this.result.titles){
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
    this.result.commissionerForJointRepresentation = $event.checked
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
/*
  additionalPatentChange($event: MatCheckboxChange) {
    this.result.additionalPatent = $event.checked
    this.additionalPatent = this.result.separatedPatent || this.result.additionalPatent
  }

  separatedPatentChange($event: MatCheckboxChange) {
    this.result.separatedPatent = $event.checked
    this.additionalPatent = this.result.separatedPatent || this.result.additionalPatent
  }
*/
  updatePatent($event: PreviousPatentDto) {
    this.result.previousPatent = $event
    this.isAdditionalPatentCompleted = $event.completed
  }

  additionalPatentOptionChanged($event: MatRadioChange) {
    if ($event.value === '1'){
      this.result.additionalPatent = true
      this.result.separatedPatent = false
    }
    else if ($event.value === '2'){
      this.result.additionalPatent = false
      this.result.separatedPatent = true
    }else{
      this.result.additionalPatent = false
      this.result.separatedPatent = false
      this.isAdditionalPatentCompleted = true
    }
    this.additionalPatent = this.result.separatedPatent || this.result.additionalPatent
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
          this.result.priorityPatent[value.id] = value

        console.log("------------------ stanje ------------- ")
        console.log(this.result.priorityPatent)
      }
    );
  }

  removeChild() {
    const index = this.vcr.indexOf(this.ref.hostView)
    if (index != -1) this.vcr.remove(index)
    this.result.priorityPatent.pop()
    console.log("------------------ stanje ------------- ")
    console.log(this.result.priorityPatent)
    this.counter--
  }

  isPriorityCompleted(){
    if (this.isAdditionalPatentCompleted)
      return false
    if (this.result.priorityPatent.length == this.counter)
      return true
    return false
  }
}

