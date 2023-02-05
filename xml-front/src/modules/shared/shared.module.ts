import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {Interceptor} from "./interceptors/interceptor.interceptor";
import {DateArrayToStringPipe} from "./pipes/date-array-to-string.pipe";
import {EntityComponent} from "./components/entity/entity.component";
import {MatFormFieldModule} from "@angular/material/form-field";
import {ReactiveFormsModule} from "@angular/forms";
import {MatLegacyRadioModule} from "@angular/material/legacy-radio";
import {MatInputModule} from "@angular/material/input";
import {MatRadioModule} from "@angular/material/radio";
import { PatentFilterTypeToStringPipe } from './pipes/patent-filter-type-to-string.pipe';



@NgModule({
  declarations: [
    DateArrayToStringPipe,
    EntityComponent,
    PatentFilterTypeToStringPipe
  ],
    imports: [
        CommonModule,
        MatFormFieldModule,
        ReactiveFormsModule,
        MatInputModule,
        MatRadioModule
    ],
    exports: [DateArrayToStringPipe, EntityComponent, PatentFilterTypeToStringPipe],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: Interceptor, multi: true }
  ]
})
export class SharedModule { }
