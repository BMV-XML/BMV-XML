import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {AuthModule} from "../auth/auth.module";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {RootComponent} from "./pages/root/root.component";
import {HttpClientModule} from "@angular/common/http";
import { OfficialHeaderComponent } from './components/official-header/official-header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PatentHeaderComponent } from './components/patent-header/patent-header.component';
import {SharedModule} from "../shared/shared.module";
import { SimpleHeaderComponent } from './components/simple-header/simple-header.component';

@NgModule({
  declarations: [
    AppComponent,
    RootComponent,
    OfficialHeaderComponent,
    PatentHeaderComponent,
    SimpleHeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AuthModule,
    BrowserAnimationsModule,
    HttpClientModule,
    NgbModule,
    SharedModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
