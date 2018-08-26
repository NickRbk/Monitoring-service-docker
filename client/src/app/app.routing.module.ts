import {RouterModule, Routes} from '@angular/router';
import {WelcomeComponent} from './welcome/welcome.component';
import {SignupComponent} from './auth/signup/signup.component';
import {LoginComponent} from './auth/login/login.component';
import {NgModule} from '@angular/core';
import {AuthGuard} from './auth/auth.guard';
import {ObjectivesComponent} from './objectives/objectives.component';
import {TweetsComponent} from './tweets/tweets.component';
import {EditObjectiveComponent} from './objectives/edit-objective/edit-objective.component';

const appRoutes: Routes = [
  {path: '', component: WelcomeComponent, pathMatch: 'full'},
  {path: 'signup', component: SignupComponent},
  {path: 'login', component: LoginComponent},
  {path: 'user/:id', component: SignupComponent, canActivate: [AuthGuard]},
  {path: 'objectives', canActivate: [AuthGuard], children: [
      {path: '', component: ObjectivesComponent},
      {path: 'new', component: EditObjectiveComponent},
      {path: ':id/edit', component: EditObjectiveComponent}
    ]},
  {path: 'media', canActivate: [AuthGuard], component: TweetsComponent},
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule],
  providers: [AuthGuard]
})

export class AppRoutingModule {}
