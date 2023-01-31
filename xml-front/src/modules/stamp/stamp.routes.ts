import {Routes} from "@angular/router";
import {RoleGuard} from "../auth/guards/role/role.guard";
import {STAMP} from "../auth/types";
import {AddStampComponent} from "./pages/add-stamp/add-stamp.component";

export const StampRoutes: Routes = [
    {
        path: 'add',
        pathMatch: 'full',
        component: AddStampComponent,
        // canActivate: [RoleGuard],
        data: {expectedRole: STAMP}
    },

]
