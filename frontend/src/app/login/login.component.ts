import { QuizService } from '../quiz.service';
import { AuthService } from '../authentication/auth.service';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private router: Router, public authService: AuthService, public quizService: QuizService) { }

  @Input() user: string;
  @Input() pwd: string;

  login() {
    // in real world app- call auth service to get the token
    this.authService.authenticateUser(this.user, this.pwd);
  }
}
