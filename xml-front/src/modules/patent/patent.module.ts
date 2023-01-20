import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddPatentComponent } from './pages/add-patent/add-patent.component';
import {RouterModule} from "@angular/router";
import {PatentRoutes} from "./patent.routes";
import { PatentRequestsComponent } from './pages/patent-requests/patent-requests.component';
import {TableModule} from "primeng/table";
import { DateArrayToStringPipe } from './pipes/date-array-to-string.pipe';
import {ButtonModule} from "primeng/button";



@NgModule({
  declarations: [
    AddPatentComponent,
    PatentRequestsComponent,
    DateArrayToStringPipe
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(PatentRoutes),
    TableModule,
    ButtonModule
  ]
})
export class PatentModule { }
