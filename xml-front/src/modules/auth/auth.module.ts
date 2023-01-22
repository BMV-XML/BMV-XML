import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './pages/login/login.component';
import {RouterModule} from "@angular/router";
import {AuthRoutes} from "./auth.routes";
import {ReactiveFormsModule} from "@angular/forms";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatButtonModule} from "@angular/material/button";
import {MatInputModule} from "@angular/material/input";
import { RegisterComponent } from './pages/register/register.component';
import {MatRadioModule} from "@angular/material/radio";
import {ToastModule} from "primeng/toast";



@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent
  ],
    imports: [
        CommonModule,
        RouterModule.forChild(AuthRoutes),
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatButtonModule,
        MatInputModule,
        MatRadioModule,
        ToastModule
    ]
})
export class AuthModule { }
