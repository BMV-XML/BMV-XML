import {Routes} from "@angular/router";
import {RoleGuard} from "../auth/guards/role/role.guard";
import {PATENT_OFFICIAL, STAMP, STAMP_OFFICIAL} from "../auth/types";
import {AddStampComponent} from "./pages/add-stamp/add-stamp.component";
import {ViewStampComponent} from "./pages/view-stamp/view-stamp.component";
import {FullStampComponent} from "./pages/full-stamp/full-stamp.component";

export const StampRoutes: Routes = [
    {
        path: 'add',
        pathMatch: 'full',
        component: AddStampComponent,
        canActivate: [RoleGuard],
        data: {expectedRole: STAMP}
    },
    {
        path: 'list',
        pathMatch: 'full',
        component: ViewStampComponent,
        canActivate: [RoleGuard],
        data: {expectedRole: STAMP_OFFICIAL}
    },
    {
        path:'full/:id',
        pathMatch: 'full',
        component: FullStampComponent,
        canActivate: [RoleGuard],
        data:{expectedRole: STAMP_OFFICIAL}
    }

]
