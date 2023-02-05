import { ENTER, COMMA, SPACE } from '@angular/cdk/keycodes';
import { Component, ComponentRef, ViewChild, ViewContainerRef } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { DateAdapter } from '@angular/material/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import * as saveAs from 'file-saver';
import { InspectSolutionComponent } from 'modules/authorship/components/inspect-solution/inspect-solution.component';
import { MetadataComponent } from 'modules/authorship/components/metadata/metadata.component';
import { AuthorshipDTO } from 'modules/authorship/models/authorship-dto';
import { SearchDTO } from 'modules/authorship/models/search-dto';
import { AuthorshipService } from 'modules/authorship/services/authorship.service';
import { FilterDto } from 'modules/patent/models/filter-dto';
import { SelectDto } from 'modules/patent/models/select-dto';
import { ViewStampSolutionComponent } from 'modules/stamp/components/view-stamp-solution/view-stamp-solution.component';
import { SimpleViewStampDto } from 'modules/stamp/models/simple-view-stamp-dto';
import { MessageService } from 'primeng/api';
import * as xml2js from "xml2js";

@Component({
  selector: 'app-authorship-solutions',
  templateUrl: './authorship-solutions.component.html',
  styleUrls: ['./authorship-solutions.component.scss'],
  providers: [MessageService]
})
export class AuthorshipSolutionsComponent {

  authorships: AuthorshipDTO[] = [];


  constructor(private authorshipService: AuthorshipService,
              private router: Router, 
              private _formBuilder: FormBuilder,
              public dialog: MatDialog,
              private readonly messageService: MessageService,
              private dateAdapter: DateAdapter<Date>) {

    this.dateAdapter.setLocale('sr-Latn');
    this.authorshipService.getAuthorshipList().subscribe(
      (res) => {
        this.showRequestsInTable(res)
      }
    )
  }

  // ------------- DOWNLOAD FILES --------------

  getPDF($event: MouseEvent, id: string) {
    $event.stopPropagation();
    this.authorshipService.getPDF(id).subscribe(
      (res) => {
        saveAs(res, id+ '.pdf');
      }
    )
  }

  getHTML($event: MouseEvent, id: string) {
    $event.stopPropagation();
    this.authorshipService.getHTML(id).subscribe(
      (res) => {
        saveAs(res, id+ '.html');
      }
    )
  }

  // ------------- SEARCH --------------

  terms: SearchDTO[] = [];
  readonly separatorKeysCodes = [ENTER, COMMA, SPACE] as const;

  add(event: any): void {
    const value = (event.value || '').trim();
    if (value) {
      this.terms.push({expression: value});
    }
    event.chipInput!.clear();
  }

  remove(term: SearchDTO): void {
    const index = this.terms.indexOf(term);

    if (index >= 0) {
      this.terms.splice(index, 1);
    }
  }

  edit(term: SearchDTO, event: any) {
    const value = event.value.trim();
    if (!value) {
      this.remove(term);
      return;
    }
    const index = this.terms.indexOf(term);
    if (index >= 0) {
      this.terms[index].expression = value;
    }
  }

  search() {
    this.authorshipService.searchBasicData(this.terms).subscribe(
      (res) => {
        this.showRequestsInTable(res)
      }
    )
  }

  private showRequestsInTable(res: string) {
    console.log('RESPONSE')
    console.log(res)
    let parser = new xml2js.Parser();
    parser.parseString(res, (err, result) => {
      this.authorships = []
      try {
        for (let i of result['List']['item']) {
          if (i.hasSolution == 'true')
            this.authorships.push(i)
        }
      } catch (err) {
        this.messageService.add({
          key: 'authorship-list-message',
          severity: 'warn',
          summary: 'Neuspešna pretraga',
          detail: 'Nema rezultata.'
        })
      }
    });

  }

  // ----------- FILTER --------------

  types: SelectDto[] = [
    {name: "Podnosilac(ime)", value: "ime_podnosilac_prijave"},
    {name: "Podnosilac(prezime)", value: "prezime_podnosilac_prijave"},
    {name: "Autor(ime)", value: "ime_autor"},
    {name: "Autor(prezime)", value: "prezime_autor"},
    {name: "Naslov", value: "naslov"},
    {name: "Datum prijave", value: "datum_prijave"},
    {name: "ID prijave", value: "id"}
  ]
  operators: SelectDto[] = [ {name: 'i', value: "i"}, {name:'ili', value:'ili'}, {name:'ne', value:"ne"}]

  lastFilter: FilterDto[] = []

  filterFormGroup = this._formBuilder.group({
    type: ['', [Validators.required]],
    text: ['', [Validators.required]],
  })

  searchMetadata() {
    console.log("-----------")
    let nextFilter : FilterDto[] = []
    let firstElement : FilterDto = {
      operator: "i",
      type: this.filterFormGroup.controls.type.value,
      value: this.filterFormGroup.controls.text.value
    }
    nextFilter.push(firstElement)
    for (let elem of this.refList){
      nextFilter.push(elem.instance.getFilterElements())
    }
    console.log(nextFilter)
    this.authorshipService.searchMetadata(nextFilter).subscribe(
      (res) => {
        this.showRequestsInTable(res)
      }
    )
    console.log(this.lastFilter)
  }

  @ViewChild("viewContainerRef", { read: ViewContainerRef }) vcr!: ViewContainerRef;
  refList: ComponentRef<MetadataComponent>[] = []

  addFilterElement() {
    let ref = this.vcr.createComponent(MetadataComponent)
    this.refList.push(ref)
    ref.instance.operator_options = this.operators;
    ref.instance.type_options = this.types;
  }

  removeFilterElement() {
    if (this.refList.length == 0)
      return
    const index = this.vcr.indexOf(this.refList[this.refList.length-1].hostView)
    if (index != -1) {this.vcr.remove(index); this.refList.pop()}
  }

  firstElement: FilterDto = {operator: "", type: "", value: ""}

  // ------------- SOLUTION --------------

  openInspectSolutionDialog($event: MouseEvent, idElement: string): void {
    $event.stopPropagation();
    let dialogRef
    dialogRef = this.dialog.open(InspectSolutionComponent, {
      width: '60%',
      height: '40%',
      data: idElement
    })

    dialogRef.afterClosed().subscribe((res) => {
      console.log('The dialog was closed')
    })
  }

  getURL(id: any) {
    return;
  }

}
