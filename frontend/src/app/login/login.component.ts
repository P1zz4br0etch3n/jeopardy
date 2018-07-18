import { QuizService } from './../quiz.service';
import { AuthService } from './../authentication/auth.service';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private router: Router, private authService: AuthService, public quitService: QuizService) { }

  @Input() user: string;
  @Input() pwd: string;

  wrongCredentials = false;

  login() {
    // in real world app- call auth service to get the token
    this.authService.authenticateUser(this.user, this.pwd);

    if (this.authService.isAuthenticated()) {
      this.router.navigate(['/choose']);
    } else {
      this.wrongCredentials = true;
    }
    console.log(localStorage.getItem('access_token'));
  }
}
