import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AddPatentComponent } from './pages/add-patent/add-patent.component';
import {RouterModule} from "@angular/router";
import {PatentRoutes} from "./patent.routes";



@NgModule({
  declarations: [
    AddPatentComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(PatentRoutes)
  ]
})
export class PatentModule { }
