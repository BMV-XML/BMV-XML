import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {AuthModule} from "../auth/auth.module";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {RootComponent} from "./pages/root/root.component";
import {HttpClientModule} from "@angular/common/http";
import { OfficialPatentHeaderComponent } from './components/official-patent-header/official-patent-header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PatentHeaderComponent } from './components/patent-header/patent-header.component';
import {SharedModule} from "../shared/shared.module";
import { SimpleHeaderComponent } from './components/simple-header/simple-header.component';
import { AuthorshipHeaderComponent } from './components/authorship-header/authorship-header.component';
import {StampHeaderComponent} from "./components/stamp-header/stamp-header.component";
import { OfficialStampHeaderComponent } from './components/official-stamp-header/official-stamp-header.component';
import { OfficialAuthorshipHeaderComponent } from './components/official-authorship-header/official-authorship-header.component';

@NgModule({
  declarations: [
    AppComponent,
    RootComponent,
    OfficialPatentHeaderComponent,
    PatentHeaderComponent,
    SimpleHeaderComponent,
    AuthorshipHeaderComponent,
    StampHeaderComponent,
    OfficialStampHeaderComponent,
    OfficialAuthorshipHeaderComponent
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
