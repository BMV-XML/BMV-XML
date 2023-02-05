import {Routes} from "@angular/router";
import {RoleGuard} from "../auth/guards/role/role.guard";
import {OFFICIAL, AUTHORSHIP} from "../auth/types";
import { AddAuthorshipComponent } from "./pages/add-authorship/add-authorship.component";
import { AuthorshipRequestsComponent } from "./pages/authorship-requests/authorship-requests.component";

export const AuthorshipRoutes: Routes = [
  {
    path: 'add',
    pathMatch: 'full',
    component: AddAuthorshipComponent,
    // canActivate: [RoleGuard],
    data: {expectedRole: AUTHORSHIP}
  },
  {
    path: 'list',
    pathMatch: 'full',
    component: AuthorshipRequestsComponent,
    // canActivate: [RoleGuard],
    // data: {expectedRole: PATENT_OFFICIAL}
  }
  
]
