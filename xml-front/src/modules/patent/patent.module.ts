import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddPatentComponent } from './pages/add-patent/add-patent.component';
import {RouterModule} from "@angular/router";
import {PatentRoutes} from "./patent.routes";
import { PatentRequestsComponent } from './pages/patent-requests/patent-requests.component';
import {TableModule} from "primeng/table";
import { DateArrayToStringPipe } from '../shared/pipes/date-array-to-string.pipe';
import {ButtonModule} from "primeng/button";
import { AddSolutionComponent } from './components/add-solution/add-solution.component';
import {MatDialogModule} from "@angular/material/dialog";
import {MatRadioModule} from "@angular/material/radio";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { ViewSolutionComponent } from './components/view-solution/view-solution.component';
import {BadgeModule} from "primeng/badge";
import {MatStepperModule} from "@angular/material/stepper";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import { EntityComponent } from './components/entity/entity.component';
import {MatCheckboxModule} from "@angular/material/checkbox";
import { PatentComponent } from './components/patent/patent.component';
import {MatDatepickerModule} from "@angular/material/datepicker";
import {DateAdapter, MatNativeDateModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {SharedModule} from "../shared/shared.module";
import {ToastModule} from "primeng/toast";



@NgModule({
  declarations: [
    AddPatentComponent,
    PatentRequestsComponent,
    AddSolutionComponent,
    ViewSolutionComponent,
    EntityComponent,
    PatentComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(PatentRoutes),
    TableModule,
    ButtonModule,
    MatDialogModule,
    MatRadioModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    BadgeModule,
    MatStepperModule,
    MatButtonModule,
    FormsModule,
    MatIconModule,
    MatCheckboxModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSelectModule,
    SharedModule,
    ToastModule
  ]
})
export class PatentModule { }
