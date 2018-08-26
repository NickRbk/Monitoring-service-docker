import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import {MaterialModule} from './material.module';
import { HeaderComponent } from './navigation/header/header.component';
import { SidenavListComponent } from './navigation/sidenav-list/sidenav-list.component';
import {FlexLayoutModule} from '@angular/flex-layout';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { LoginComponent } from './auth/login/login.component';
import { SignupComponent } from './auth/signup/signup.component';
import { WelcomeComponent } from './welcome/welcome.component';
import {AuthService} from './shared/service/auth.service';
import {AppRoutingModule} from './app.routing.module';
import {AuthInterceptor} from './auth/auth-interceptor';
import { ObjectivesComponent } from './objectives/objectives.component';
import { TweetsComponent } from './tweets/tweets.component';
import {ObjectivesService} from './shared/service/objectives.service';
import { EditObjectiveComponent } from './objectives/edit-objective/edit-objective.component';
import {DateTransformerPipe} from './shared/pipes/dateTransformer.pipe';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    SidenavListComponent,
    LoginComponent,
    SignupComponent,
    WelcomeComponent,
    ObjectivesComponent,
    TweetsComponent,
    EditObjectiveComponent,
    DateTransformerPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    AuthService,
    ObjectivesService,
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
