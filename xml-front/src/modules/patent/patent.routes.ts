import {Routes} from "@angular/router";
import {AddPatentComponent} from "./pages/add-patent/add-patent.component";
import {PatentRequestsComponent} from "./pages/patent-requests/patent-requests.component";
import {RoleGuard} from "../auth/guards/role/role.guard";
import {OFFICIAL, PATENT} from "../auth/types";

export const PatentRoutes: Routes = [
  {
    path: 'add',
    pathMatch: 'full',
    component: AddPatentComponent,
    canActivate: [RoleGuard],
    data: {expectedRole: PATENT}
  },
  {
    path: 'list',
    pathMatch: 'full',
    component: PatentRequestsComponent,
    canActivate: [RoleGuard],
    data: {expectedRole: OFFICIAL}
  },
]
