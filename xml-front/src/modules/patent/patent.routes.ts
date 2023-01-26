import {Routes} from "@angular/router";
import {AddPatentComponent} from "./pages/add-patent/add-patent.component";
import {PatentRequestsComponent} from "./pages/patent-requests/patent-requests.component";
import {RoleGuard} from "../auth/guards/role/role.guard";
import {OFFICIAL, PATENT} from "../auth/types";
import {FullPatentComponent} from "./pages/full-patent/full-patent.component";

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
  },{
  path:'full/:id',
    pathMatch: 'full',
    component: FullPatentComponent
  }
]
