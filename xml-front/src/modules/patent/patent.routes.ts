import {Routes} from "@angular/router";
import {AddPatentComponent} from "./pages/add-patent/add-patent.component";
import {PatentRequestsComponent} from "./pages/patent-requests/patent-requests.component";
import {RoleGuard} from "../auth/guards/role/role.guard";
import {OFFICIAL, PATENT, PATENT_OFFICIAL} from "../auth/types";
import {FullPatentComponent} from "./pages/full-patent/full-patent.component";
import {PatentListComponent} from "./pages/patent-list/patent-list.component";

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
    data: {expectedRole: PATENT_OFFICIAL}
  },
  {
    path: 'view',
    pathMatch: 'full',
    component: PatentListComponent,
    canActivate: [RoleGuard],
    data: {expectedRole: PATENT}
  },{
  path:'full/:id',
    pathMatch: 'full',
    component: FullPatentComponent
  }
]
