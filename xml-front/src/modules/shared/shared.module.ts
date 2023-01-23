import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {Interceptor} from "./interceptors/interceptor.interceptor";
import {DateArrayToStringPipe} from "./pipes/date-array-to-string.pipe";



@NgModule({
  declarations: [
    DateArrayToStringPipe
  ],
  imports: [
    CommonModule
  ],
  exports:[DateArrayToStringPipe],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true }
  ]
})
export class SharedModule { }
