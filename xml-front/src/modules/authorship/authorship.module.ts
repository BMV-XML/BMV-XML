import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddAuthorshipComponent } from './pages/add-authorship/add-authorship.component';
import {ToastModule} from "primeng/toast";
import {RouterModule} from "@angular/router";
import {TableModule} from "primeng/table";
import { DateArrayToStringPipe } from '../shared/pipes/date-array-to-string.pipe';
import {ButtonModule} from "primeng/button";
import {MatDialogModule} from "@angular/material/dialog";
import {MatRadioModule} from "@angular/material/radio";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BadgeModule} from "primeng/badge";
import {MatStepperModule} from "@angular/material/stepper";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {DateAdapter, MatNativeDateModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {SharedModule} from "../shared/shared.module";
import { AuthorshipRoutes } from './authorship.routes';
import { CommissionerComponent } from './components/commissioner/commissioner.component';
import {MatDividerModule} from '@angular/material/divider';
import { AuthorComponent } from './components/author/author.component';
import { AuthorsWorkComponent } from './components/authors-work/authors-work.component';


@NgModule({
  declarations: [
    AddAuthorshipComponent,
    CommissionerComponent,
    AuthorComponent,
    AuthorsWorkComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(AuthorshipRoutes),
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
    ToastModule,
    MatDividerModule
  ]
})
export class AuthorshipModule { }
