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
  },
  {
    path:'stamp',
    children:[
      {
        path:'',
        component: RootComponent,
        loadChildren:()=> import('../stamp/stamp.module').then((m)=>m.StampModule)
      }
    ]
  },
  {
    path:'authorship',
    children:[
      {
        path:'',
        component: RootComponent,
        loadChildren:()=> import('../authorship/authorship.module').then((m) => m.AuthorshipModule)
      }
    ]
  }
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
