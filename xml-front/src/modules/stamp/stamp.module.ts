import {NgModule} from "@angular/core";
import {AddPatentComponent} from "../patent/pages/add-patent/add-patent.component";
import {PatentRequestsComponent} from "../patent/pages/patent-requests/patent-requests.component";
import {AddSolutionComponent} from "../patent/components/add-solution/add-solution.component";
import {ViewSolutionComponent} from "../patent/components/view-solution/view-solution.component";
import {EntityComponent} from "../shared/components/entity/entity.component";
import {PatentComponent} from "../patent/components/patent/patent.component";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {PatentRoutes} from "../patent/patent.routes";
import {TableModule} from "primeng/table";
import {ButtonModule} from "primeng/button";
import {MatDialogModule} from "@angular/material/dialog";
import {MatRadioModule} from "@angular/material/radio";
import {MatFormFieldControl, MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BadgeModule} from "primeng/badge";
import {MatStepperModule} from "@angular/material/stepper";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatSelectModule} from "@angular/material/select";
import {SharedModule} from "../shared/shared.module";
import {ToastModule} from "primeng/toast";
import {AddStampComponent} from './pages/add-stamp/add-stamp.component';
import {StampRoutes} from "./stamp.routes";
import { ApplicantComponent } from './components/applicant/applicant.component';
import {MatChipsModule} from "@angular/material/chips";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {MatTableModule} from "@angular/material/table";
import { ViewStampComponent } from './pages/view-stamp/view-stamp.component';
import { AddStampSolutionComponent } from './components/add-stamp-solution/add-stamp-solution.component';
import { ViewStampSolutionComponent } from './components/view-stamp-solution/view-stamp-solution.component';
import { FullStampComponent } from './pages/full-stamp/full-stamp.component';

@NgModule({
    declarations: [
        AddStampComponent,
        ApplicantComponent,
        ViewStampComponent,
        AddStampSolutionComponent,
        ViewStampSolutionComponent,
        FullStampComponent
    ],
    imports: [
        CommonModule,
        RouterModule.forChild(StampRoutes),
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
        MatChipsModule,
        MatAutocompleteModule,
        MatTableModule
    ]
})
export class StampModule {
}
