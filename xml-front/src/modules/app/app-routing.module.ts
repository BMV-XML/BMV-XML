import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {RootComponent} from "./pages/root/root.component";

const routes: Routes = [
  {
    path: 'auth',
    children: [
      {
        path: '',
        component: RootComponent,
        loadChildren: () =>
          import('../auth/auth.module').then((m) => m.AuthModule)
      }
    ]
  },{
    path: 'patent',
    children: [
      {
        path: '',
        component: RootComponent,
        loadChildren: () =>
          import('../patent/patent.module').then((m) => m.PatentModule)
      }
    ]
  }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
