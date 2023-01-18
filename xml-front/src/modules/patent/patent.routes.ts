import {Routes} from "@angular/router";
import {AddPatentComponent} from "./pages/add-patent/add-patent.component";

export const PatentRoutes: Routes = [
  {
    path: 'add',
    pathMatch: 'full',
    component: AddPatentComponent
  },
]
