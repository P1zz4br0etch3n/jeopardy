import { AuthGuard } from './authentication/auth.guard';
import { AuthService } from './authentication/auth.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import {FormsModule} from '@angular/forms';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatIconModule} from '@angular/material/icon';
import {HttpClientModule} from '@angular/common/http';


import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { LoginComponent } from './login/login.component';
import { GameComponent } from './game/game.component';
import { AppRoutingModule } from './/app-routing.module';
import { CreateComponent } from './create/create.component';
import { ChooseComponent } from './choose/choose.component';
import { QuizService } from './quiz.service';
import { ShowComponent } from './show/show.component';
import { RegisterComponent } from './register/register.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    GameComponent,
    CreateComponent,
    ChooseComponent,
    ShowComponent,
    RegisterComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatButtonModule,
    AppRoutingModule,
    MatSlideToggleModule,
    FormsModule,
    MatToolbarModule,
    MatGridListModule,
    MatIconModule,
    HttpClientModule
  ],
  providers: [QuizService, AuthService, AuthGuard],
  exports: [AppComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
