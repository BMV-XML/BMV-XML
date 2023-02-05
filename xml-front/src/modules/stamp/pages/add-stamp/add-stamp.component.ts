import {Component, ComponentRef, ElementRef, ViewChild, ViewContainerRef} from '@angular/core';
import {EntityDto} from "../../../patent/models/entity-dto";
import {StampRequestDto} from "../../models/stamp-request-dto";
import {MatRadioChange} from "@angular/material/radio";
import {ApplicantComponent} from "../../components/applicant/applicant.component";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {stringAndNumber} from "../../../patent/validators";
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {map, Observable, startWith} from "rxjs";
import {MatChipInputEvent} from "@angular/material/chips";
import {MatAutocompleteSelectedEvent} from "@angular/material/autocomplete";
import {GoodsAndClasses} from "../goods-and-classes";
import {DomSanitizer} from "@angular/platform-browser";
import {StampService} from "../../services/stamp-service/stamp.service";
import * as xml2js from "xml2js";
import {ApplicantsDto} from "../../models/applicants-dto";
import {ColorsDto} from "../../models/colors-dto";
import {GoodsAndClassesDto} from "../../models/goods-and-classes-dto";
import {Message, MessageService} from "primeng/api";

export interface PeriodicElement {
    name: string;
    position: number;
    path: string;
    submittedAttachment: string,
    // attachmentDto: AttachmentDto;
}


@Component({
    selector: 'app-add-stamp',
    templateUrl: './add-stamp.component.html',
    styleUrls: ['./add-stamp.component.scss'],
    providers: [MessageService]
})
export class AddStampComponent {

    isLinear = true;
    isLowerCompleted: boolean = false;
    isCommonRepresentativeCompleted: boolean = false
    isHiddenOtherKind: boolean = true;
    isHiddenYesPriority: boolean = true;
    isStampCompleted: boolean = true;
    isFilesUploaded: boolean = true;
    formGroup: FormGroup;
    otherKindFormControl = new FormControl('', [Validators.required, stringAndNumber])
    descriptionFormControl = new FormControl('', [Validators.required, stringAndNumber])
    transliterationFormControl = new FormControl('', [stringAndNumber])
    translationFormControl = new FormControl('', [stringAndNumber])
    priorityFormControl = new FormControl('', [stringAndNumber])


    @ViewChild('colorInput') colorInput: ElementRef<HTMLInputElement> | undefined;
    @ViewChild('goodInput') goodInput: ElementRef<HTMLInputElement> | undefined;

    constructor(private _formBuilder: FormBuilder,
                private readonly sanitizer: DomSanitizer,
                private readonly stampService: StampService,
                private messageService: MessageService) {
        this.formGroup = this._formBuilder.group({
            otherKindFormControl: ['', [Validators.required, stringAndNumber]],
            descriptionFormControl: ['', [Validators.required, stringAndNumber]],
            transliterationFormControl: ['', [stringAndNumber]],
            translationFormControl: ['', [stringAndNumber]],
            priorityFormControl: ['', [stringAndNumber]]
        });

        this.filteredColors = this.colorCtrl.valueChanges.pipe(startWith(null),
            map((color: string | null) => (color ? this._filterColor(color) : this.allColors.slice())),
        );

        this.filteredGoods = this.goodCtrl.valueChanges.pipe(startWith(null),
            map((good: string | null) => (good ? this._filterGood(good) : this.allGoods.slice())),
        );
        this.allGoods = GoodsAndClasses
    }

    entity: EntityDto = {
        id: 0,
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


    result: StampRequestDto = {
        applicants: "",
        lowyer: this.entity,
        commonRepresentative: this.entity,
        stamp: {
            colors: "",
            description: "",
            goodsAndServicesClass: "",
            kind: "verbalni znak",
            priority: "",
            type: "INDIVIDUALNI",
            transliteration: "",
            translation: "",
            image: ""
        },
        recepient: {street: "", city: "", number: "", country: "", postalNumber: "", name: ""},
        attachmentData: {
            exampleStamp: {path: "", submittedAttachment: "NE", name: "", position: ""},
            listOfGoodsAndServices: {path: "", submittedAttachment: "NE", name: "", position: ""},
            authority: {path: "", submittedAttachment: "NE", name: "", position: ""},
            generalAuthorityAddedBefore: {path: "", submittedAttachment: "NE", name: "", position: ""},
            authorityWillBeAddedAfter: {path: "", submittedAttachment: "NE", name: "", position: ""},
            generalActOnCollectiveStampOrGuaranteeStamp: {path: "", submittedAttachment: "NE", name: "", position: ""},
            proofOfRightOfPriority: {path: "", submittedAttachment: "NE", name: "", position: ""},

            proofOfTaxPayment: {path: "", submittedAttachment: "NE", name: "", position: ""},
        }
    }

    applicantsDto: ApplicantsDto = {
        applicants: []
    }

    colorsDto: ColorsDto = {
        colors: []
    }

    goodsAndClasses: GoodsAndClassesDto = {
        goodsAndServicesClass: []
    }

    products: PeriodicElement[] = [
        {position: 1, name: 'Primerak znaka', path: "", submittedAttachment: "NE"},
        {position: 2, name: 'Spisak robe i usluga', path: "", submittedAttachment: "NE"},
        {position: 3, name: 'Punomoćje', path: "", submittedAttachment: "NE"},
        {position: 4, name: 'Generalno punomoćje ranije priloženo', path: "", submittedAttachment: "NE"},
        {position: 5, name: 'Punomoćje će biti naknadno dostavljeno', path: "", submittedAttachment: "NE"},
        {position: 6, name: 'Opšti akt o kolektivnom/žigu garancije', path: "", submittedAttachment: "NE"},
        {position: 7, name: 'Dokaz o pravu prvenstva', path: "", submittedAttachment: "NE"},
        {position: 8, name: 'Dokaz o uplati takse', path: "", submittedAttachment: "NE"},

    ];


    @ViewChild("applicantContainerRef", {read: ViewContainerRef}) vcr!: ViewContainerRef;
    ref!: ComponentRef<ApplicantComponent>
    counter: number = 0


    separatorKeysCodes: number[] = [ENTER, COMMA];
    colorCtrl = new FormControl('');
    filteredColors: Observable<string[]>;
    allColors: string[] = ['crvena', 'žuta', 'zelena', 'plava', 'bela', 'crna', 'siva', 'narandžasta', 'ljubičasta', 'roze',
        'oker', 'bordo', 'tirkizna'];

    goodCtrl = new FormControl('');
    filteredGoods: Observable<string[]>;
    allGoods: string[] = []

    signPhoto: any


    addApplicant() {
        this.ref = this.vcr.createComponent(ApplicantComponent)
        this.ref.instance.applicant.id = this.counter
        this.ref.instance.id = this.counter
        this.counter++
        console.log("id od applikanta")
        console.log(this.ref.instance.applicant.id)
        this.ref.instance.previousApplicant.subscribe(
            value => {
                if (value.completed)
                    console.log(this.counter)
                this.applicantsDto.applicants[value.id] = value
                console.log("------------------ stanje ------------- ")
                console.log("counnter: ")
                console.log(this.counter)
                console.log(this.applicantsDto)
            }
        );
    }

    removeApplicant() {
        const index = this.vcr.indexOf(this.ref.hostView)
        if (index != -1) this.vcr.remove(index)
        if (this.applicantsDto.applicants.length === this.counter) {
            this.counter--
            this.applicantsDto.applicants.pop()
        }
        console.log("------------------ stanje ------------- ")
        console.log(this.result)
    }

    isApplicantCompleted(): boolean {
        if (this.applicantsDto.applicants.length > 0) {
            return true;
        }
        return false;
    }

    addLower($event: EntityDto) {
        this.result.lowyer = $event
        this.isLowerCompleted = $event.completed;
        console.log(this.isLowerCompleted)
        this.isLowerCompleted = true;
    }

    addCommonRepresentative($event: EntityDto) {
        this.result.commonRepresentative = $event
        this.isCommonRepresentativeCompleted = $event.completed
        console.log(this.isCommonRepresentativeCompleted)
        this.isCommonRepresentativeCompleted = true;
    }

    optionChanged($event: MatRadioChange): void {
        if ($event.value === '1')
            this.result.stamp.type = "INDIVIDUALNI"
        else if ($event.value === '2')
            this.result.stamp.type = "KOLEKTIVNI"
        else {
            this.result.stamp.type = "ZIG_GARANCIJE"
        }
    }

    optionChangedKind($event: MatRadioChange) {
        if ($event.value === '1') {
            this.result.stamp.kind = "verbalni znak"
            this.isHiddenOtherKind = true
        } else if ($event.value === '2') {
            this.result.stamp.kind = "grafički znak"
            this.isHiddenOtherKind = true
        } else if ($event.value === '3') {
            this.result.stamp.kind = "kombinovani znak"
            this.isHiddenOtherKind = true
        } else if ($event.value === '4') {
            this.result.stamp.kind = "trodimenzionalni znak"
            this.isHiddenOtherKind = true
        } else {
            this.isHiddenOtherKind = false
        }
    }

   /* check() {
        if (this.formGroup.valid) {
            console.log(this.otherKindFormControl.value)
            //  if (this.otherKindFormControl.valid)
            this.result.stamp.kind = this.otherKindFormControl.value
            //  if (this.descriptionFormControl.valid)
            this.result.stamp.description = this.descriptionFormControl.value
            //  if (this.transliterationFormControl.valid)
            this.result.stamp.transliteration = this.transliterationFormControl.value
            //  if (this.translationFormControl.valid)
            this.result.stamp.translation = this.translationFormControl.value
            if (this.result.stamp.priority === "DA")
                this.result.stamp.priority = this.priorityFormControl.value;
        }
    }*/

    colorAlreadyAdded(color: string): boolean {
        for (let c of this.colorsDto.colors) {
            if (c === color) {
                return true
            }
        }
        return false;
    }

    addColor($event: MatChipInputEvent): void {
        const value = ($event.value || '').trim();
        // Add our fruit
        if (value && !this.colorAlreadyAdded(value)) {
            this.colorsDto.colors.push(value);
        }
        // Clear the input value
        $event.chipInput!.clear();
        this.colorCtrl.setValue(null);
    }

    removeColor(color: string): void {
        const index = this.colorsDto.colors.indexOf(color);
        if (index >= 0) {
            this.colorsDto.colors.splice(index, 1);
        }
    }

    selected(event: MatAutocompleteSelectedEvent): void {
        if (!this.colorAlreadyAdded(event.option.viewValue))
            this.colorsDto.colors.push(event.option.viewValue);
        if (this.colorInput !== undefined)
            this.colorInput.nativeElement.value = '';
        this.colorCtrl.setValue(null);
    }

    private _filterColor(value: string): string[] {
        const filterValue = value.toLowerCase();
        return this.allColors.filter(color => color.toLowerCase().includes(filterValue));
    }

    addGood($event: MatChipInputEvent) {
        const value = ($event.value || '').trim();
        // Add our fruit
        if (value && !this.goodAlreadyAdded(value)) {
            this.goodsAndClasses.goodsAndServicesClass.push(value);
        }
        // Clear the input value
        $event.chipInput!.clear();
        this.goodCtrl.setValue(null);

    }

    private goodAlreadyAdded(good: string) {
        for (let c of this.goodsAndClasses.goodsAndServicesClass) {
            if (c === good) {
                return true
            }
        }
        return false;
    }

    removeGood(good: string) {
        const index = this.goodsAndClasses.goodsAndServicesClass.indexOf(good);
        if (index >= 0) {
            this.goodsAndClasses.goodsAndServicesClass.splice(index, 1);
        }
    }

    selectedGood($event: MatAutocompleteSelectedEvent) {
        if (!this.goodAlreadyAdded($event.option.viewValue))
            this.goodsAndClasses.goodsAndServicesClass.push($event.option.viewValue);
        if (this.goodInput !== undefined)
            this.goodInput.nativeElement.value = '';
        this.goodCtrl.setValue(null);
    }

    private _filterGood(good: string): string[] {
        const filterValue = good.toLowerCase();
        return this.allGoods.filter(good => good.toLowerCase().includes(filterValue));
    }

    selectPhoto($event: any) {
        const data = new FormData()
        data.append('files', $event.target.files[0])
        this.stampService.addPhoto(data).subscribe((res) => {
            var parser = new xml2js.Parser();
            parser.parseString(res, (err, result) => {
                console.log("ono sto dobijem sa backa posle slanja slike")
                console.log(res)
                console.log(result['PhotoFileDTO']['path'][0])
                this.signPhoto = result['PhotoFileDTO']['path'][0]
                this.result.stamp.image = result['PhotoFileDTO']['path'][0]
                // this.messageService.add({ key: 'update-profile-message', severity: 'success', summary: 'Uspešna promena slike', detail: 'Profilna slika je uspešno ažurirana.' })
            })
        })
    }

    selectFile($event: any, element: PeriodicElement) {
        console.log(" SELECT FILE")
        const data = new FormData()
        data.append('files', $event.target.files[0])
        this.stampService.addFile(data).subscribe((res) => {
            var parser = new xml2js.Parser();
            parser.parseString(res, (err, result) => {
                console.log("ono sto dobijem sa backa posle slanja slike")
                console.log(res)
                console.log(result['PhotoFileDTO']['path'][0])
                this.setFile(element, result['PhotoFileDTO']['path'][0])
            })
        })
    }

    private setFile(element: PeriodicElement, path: string) {
        if (element.name === "Primerak znaka") {
            this.result.attachmentData.exampleStamp.submittedAttachment = "DA"
            this.result.attachmentData.exampleStamp.path = path
        } else if (element.name === "Spisak robe i usluga") {
            this.result.attachmentData.listOfGoodsAndServices.submittedAttachment = "DA"
            this.result.attachmentData.listOfGoodsAndServices.path = path
        } else if (element.name === "Punomoćje") {
            this.result.attachmentData.authority.submittedAttachment = "DA"
            this.result.attachmentData.authority.path = path
        } else if (element.name === "Generalno punomoćje ranije priloženo") {
            this.result.attachmentData.generalAuthorityAddedBefore.submittedAttachment = "DA"
            this.result.attachmentData.generalAuthorityAddedBefore.path = path
        } else if (element.name === "Punomoćje će biti naknadno dostavljeno") {
            this.result.attachmentData.authorityWillBeAddedAfter.submittedAttachment = "DA"
            this.result.attachmentData.authorityWillBeAddedAfter.path = path
        } else if (element.name === "Opšti akt o kolektivnom/žigu garancije") {
            this.result.attachmentData.generalActOnCollectiveStampOrGuaranteeStamp.submittedAttachment = "DA"
            this.result.attachmentData.generalActOnCollectiveStampOrGuaranteeStamp.path = path
        } else if (element.name === "Dokaz o pravu prvenstva") {
            this.result.attachmentData.proofOfRightOfPriority.submittedAttachment = "DA"
            this.result.attachmentData.proofOfRightOfPriority.path = path
        } else if (element.name === "Dokaz o uplati takse") {
            this.result.attachmentData.proofOfTaxPayment.submittedAttachment = "DA"
            this.result.attachmentData.proofOfTaxPayment.path = path
        }
        element.submittedAttachment = "DA"
        element.path = path
    }


    submitRequest() {
        this.stampService.submitRequest(this.result, this.applicantsDto, this.colorsDto, this.goodsAndClasses).subscribe(res => {
            console.log(res);
            let parser = new xml2js.Parser();
            parser.parseString(res, (err, result) => {
                console.log("**********************")
                if (result['StatusDTO']['successful'][0] === 'true') {
                    this.messageService.add({
                        key: 'add-stamp-message',
                        severity: 'success',
                        summary: 'Uspešno ste podneli zahtev'
                    })
                } else {
                    this.messageService.add({
                        key: 'add-stamp-message',
                        severity: 'warn',
                        summary: 'Nismo uspeli da sačuvamo zahtev',
                        detail: 'Proverite podatke koji ste dali ili pokušajte malo kasnije da podneste zahtev.'
                    })
                }
            })
        })
    }

    optionChangedPriority($event: MatRadioChange) {
        if ($event.value === '1') {
            console.log(" YYYYYYYYYYYYY za prioritet")
            this.result.stamp.priority = "DA"
            this.isHiddenYesPriority = false;
        } else {
            this.result.stamp.priority = ""
            this.isHiddenYesPriority = true;
        }
    }

    check() {
        console.log("SEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEt")
        console.log(this.otherKindFormControl.value)
        console.log(this.formGroup.controls["otherKindFormControl"].value)
        if (this.formGroup.controls["otherKindFormControl"].valid){
            console.log("UDJEEEEE")
            this.result.stamp.kind = this.formGroup.controls["otherKindFormControl"].value
            console.log(this.result.stamp.kind)
        }
        if (this.formGroup.controls["descriptionFormControl"].valid)
            this.result.stamp.description = this.formGroup.controls["descriptionFormControl"].value
        if (this.formGroup.controls["transliterationFormControl"].valid)
            this.result.stamp.transliteration = this.formGroup.controls["transliterationFormControl"].value
        if (this.formGroup.controls["translationFormControl"].valid)
            this.result.stamp.translation = this.formGroup.controls["translationFormControl"].value
        if (this.result.stamp.priority === "DA")
            this.result.stamp.priority = this.formGroup.controls["priorityFormControl"].value;
    }

}


