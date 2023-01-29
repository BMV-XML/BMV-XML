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

@Component({
    selector: 'app-add-stamp',
    templateUrl: './add-stamp.component.html',
    styleUrls: ['./add-stamp.component.scss']
})
export class AddStampComponent {

    isLinear = true;
    isLowerCompleted: boolean = true
    isCommonRepresentativeCompleted: boolean = true
    isHiddenOtherKind: boolean = true;
    isStampCompleted: boolean = true;
    formGroup: FormGroup;
    otherKindFormControl = new FormControl('', [Validators.required, stringAndNumber])
    descriptionFormControl = new FormControl('', [Validators.required, stringAndNumber])
    transliterationFormControl = new FormControl('', [stringAndNumber])
    translationFormControl = new FormControl('', [stringAndNumber])


    @ViewChild('colorInput') colorInput: ElementRef<HTMLInputElement> | undefined;
    @ViewChild('goodInput') goodInput: ElementRef<HTMLInputElement> | undefined;

    constructor(private _formBuilder: FormBuilder,
                private readonly sanitizer: DomSanitizer,
                private readonly stampService: StampService) {
        this.formGroup = this._formBuilder.group({
            otherKind: ['', [Validators.required, stringAndNumber]],
            description: ['', [Validators.required, stringAndNumber]],
            transliteration: ['', [stringAndNumber]],
            translation: ['', [stringAndNumber]]
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
        applicants: [],
        lowyer: this.entity,
        commonRepresentative: this.entity,
        stamp: {
            colors: [],
            description: "",
            goodsAndServicesClass: [],
            kind: "",
            priority: "",
            type: "",
            transliteration: "",
            translation: ""
        },
        recepient: {street: "", city: "", number: "", country: "", postalNumber: "", name: ""}
    }


    @ViewChild("applicantContainerRef", {read: ViewContainerRef}) vcr!: ViewContainerRef;
    ref!: ComponentRef<ApplicantComponent>
    counter: number = 0


    separatorKeysCodes: number[] = [ENTER, COMMA];
    colorCtrl = new FormControl('');
    filteredColors: Observable<string[]>;
    allColors: string[] = ['crvena', 'žuta', 'zelena', 'plava', 'bela', 'crna', 'siva', 'narandžasta', 'ljubičasta', 'roze',
        'oker', 'bordo', 'tirkizna'];

    goodCtrl =  new FormControl('');
    filteredGoods:  Observable<string[]>;
    allGoods : string[] = []

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
                this.result.applicants[value.id] = value
                console.log("------------------ stanje ------------- ")
                console.log("counnter: ")
                console.log(this.counter)
                console.log(this.result)
            }
        );
    }

    removeApplicant() {
        const index = this.vcr.indexOf(this.ref.hostView)
        if (index != -1) this.vcr.remove(index)
        if (this.result.applicants.length === this.counter) {
            this.counter--
            this.result.applicants.pop()
        }
        console.log("------------------ stanje ------------- ")
        console.log(this.result)
    }

    isApplicantCompleted(): boolean {
        if (this.result.applicants.length > 0) {
            return true;
        }
        return false;
    }

    addLower($event: EntityDto) {
        this.result.lowyer = $event
        this.isLowerCompleted = $event.completed;
        console.log(this.isLowerCompleted)
    }

    addCommonRepresentative($event: EntityDto) {
        this.result.commonRepresentative = $event
        this.isCommonRepresentativeCompleted = $event.completed
        console.log(this.isCommonRepresentativeCompleted)
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

    check() {
        if (this.formGroup.valid) {
            console.log(this.otherKindFormControl.value)
            if (this.otherKindFormControl.valid)
                this.result.stamp.kind = this.otherKindFormControl.value
            if (this.descriptionFormControl.value)
                this.result.stamp.description = this.descriptionFormControl.value
            if (this.transliterationFormControl.valid)
                this.result.stamp.transliteration = this.transliterationFormControl.value
            if (this.translationFormControl.valid)
                this.result.stamp.translation = this.translationFormControl.value
        }
    }

    colorAlreadyAdded(color:string) : boolean{
        for(let c of this.result.stamp.colors){
            if(c===color){
                return true
            }
        }
        return false;
    }

    addColor($event: MatChipInputEvent): void {
        const value = ($event.value || '').trim();
        // Add our fruit
        if (value && !this.colorAlreadyAdded(value)) {
            this.result.stamp.colors.push(value);
        }
        // Clear the input value
        $event.chipInput!.clear();
        this.colorCtrl.setValue(null);
    }

    removeColor(color: string): void {
        const index = this.result.stamp.colors.indexOf(color);
        if (index >= 0) {
            this.result.stamp.colors.splice(index, 1);
        }
    }

    selected(event: MatAutocompleteSelectedEvent): void {
        if(!this.colorAlreadyAdded(event.option.viewValue))
            this.result.stamp.colors.push(event.option.viewValue);
        if(this.colorInput !== undefined)
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
            this.result.stamp.goodsAndServicesClass.push(value);
        }
        // Clear the input value
        $event.chipInput!.clear();
        this.goodCtrl.setValue(null);

    }

    private goodAlreadyAdded(good: string) {
        for(let c of this.result.stamp.goodsAndServicesClass){
            if(c===good){
                return true
            }
        }
        return false;
    }

    removeGood(good: string) {
        const index = this.result.stamp.goodsAndServicesClass.indexOf(good);
        if (index >= 0) {
            this.result.stamp.goodsAndServicesClass.splice(index, 1);
        }
    }

    selectedGood($event: MatAutocompleteSelectedEvent) {
        if(!this.goodAlreadyAdded($event.option.viewValue))
            this.result.stamp.goodsAndServicesClass.push($event.option.viewValue);
        if(this.goodInput !== undefined)
            this.goodInput.nativeElement.value = '';
        this.goodCtrl.setValue(null);
    }

    private _filterGood(good: string):string[] {
        const filterValue = good.toLowerCase();
        return this.allGoods.filter(good => good.toLowerCase().includes(filterValue));
    }

    selectPhoto($event: any) {
        const data = new FormData()
        data.append('files', $event.target.files[0])
        this.stampService.addPhoto(data).subscribe({
            next: (res: Blob) => {
                console.log("ono sto dobijem sa backa posle slanja slike")
                console.log(res)
                const objectURL = URL.createObjectURL(res)
                this.signPhoto = this.sanitizer.bypassSecurityTrustUrl(objectURL)
               // this.messageService.add({ key: 'update-profile-message', severity: 'success', summary: 'Uspešna promena slike', detail: 'Profilna slika je uspešno ažurirana.' })
            }
        })
    }
}
