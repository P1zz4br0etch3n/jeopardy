import { AuthGuard } from './authentication/auth.guard';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { GameComponent } from './game/game.component';
import { CreateComponent } from './create/create.component';
import { ChooseComponent } from './choose/choose.component';
import { ShowComponent } from './show/show.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'game', component: GameComponent, canActivate: [AuthGuard] },
  { path: 'create', component: CreateComponent, canActivate: [AuthGuard] },
  { path: 'choose', component: ChooseComponent, canActivate: [AuthGuard] },
  { path: 'show', component: ShowComponent, canActivate: [AuthGuard] },
  { path: 'register', component: RegisterComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes, {enableTracing: true}) ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}
