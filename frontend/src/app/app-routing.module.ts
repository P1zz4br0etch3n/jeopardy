import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { GameComponent } from './game/game.component';
import { CreateComponent } from './create/create.component';
import { ChooseComponent } from './choose/choose.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'game', component: GameComponent },
  { path: 'create', component: CreateComponent },
  { path: 'choose', component: ChooseComponent }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes, {enableTracing: true}) ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {}
