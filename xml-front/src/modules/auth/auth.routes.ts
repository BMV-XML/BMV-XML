import {LoginComponent} from "./pages/login/login.component";
import {Routes} from "@angular/router";
import {RegisterComponent} from "./pages/register/register.component";

export const AuthRoutes: Routes = [
  {
    path: 'login',
    pathMatch: 'full',
    component: LoginComponent
  },
  {
    path: 'register',
    pathMatch: 'full',
    component: RegisterComponent
  },
]
