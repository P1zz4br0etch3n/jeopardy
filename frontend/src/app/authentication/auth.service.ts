import {Injectable} from '@angular/core';
import {HttpHeaders, HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';


@Injectable()
export class AuthService {

  loggedin = false;
  loginData: Login;
  wrongCredentials = false;
  name = 'test';

  constructor(private http: HttpClient, private router: Router) {
  }

  authenticateUser(username: string, password: string) {

    // call API to authenticate user
    this.name = username;
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    this.http.post('rest/login', '{"username":"' + username + '", "password":"' + password + '"}',
      httpOptions).subscribe((data: Login) => {
        this.loggedin = true;
        this.loginData = data;
        localStorage.setItem("loginData", JSON.stringify(data));
        localStorage.setItem('username', username);
        this.router.navigate(['/choose']);
      },
      (err: HttpErrorResponse) => {
        console.log(err);
        this.wrongCredentials = true;
      });

  }

  logout() {

    // call API to logout user

    // remove localStorage session data
    localStorage.removeItem('loginData');
    localStorage.removeItem('username');
    this.loggedin = false;
    this.router.navigate(['/login']);
  }


  isAuthenticated(): Boolean {
    if (!this.loginData) {
      // maybe page reload, try to fetch from localStorage
      this.loginData = JSON.parse(localStorage.getItem("loginData"));
      if (this.loginData) {
        // session restored
        this.loggedin = true;
        this.name = localStorage.getItem('username');
      } else {
        // still no session
        return false;
      }
    }
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    // get the auth token from localStorage
    this.http.post('rest/validateToken', '{"authToken":"' + this.loginData.authToken + '"}',
      httpOptions).subscribe((data: Validator) => {
        if (data.valid === false) {
          this.logout();
        }
      },
      (err: HttpErrorResponse) => {
        console.log(err);
        this.logout();
      });
    return this.loggedin;
  }
}

interface Login {
  authToken: string;
  userId: number;
}

interface Validator {
  valid: Boolean;
}
