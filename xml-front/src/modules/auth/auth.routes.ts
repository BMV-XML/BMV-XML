import {LoginComponent} from "./pages/login/login.component";
import {Routes} from "@angular/router";
import {RegisterComponent} from "./pages/register/register.component";
import {LoginGuard} from "./guards/login/login.guard";

export const AuthRoutes: Routes = [
  {
    path: 'login',
    pathMatch: 'full',
    component: LoginComponent,
    canActivate: [LoginGuard]
  },
  {
    path: 'register',
    pathMatch: 'full',
    component: RegisterComponent,
    canActivate: [LoginGuard]
  },
]
