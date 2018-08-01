import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';



@Injectable()
export class AuthService {

    loggedin = false;
    loginData: Login;
    wrongCredentials = false;
    name = 'test';
    constructor(private http: HttpClient, private router: Router) { }

    authenticateUser(username: string, password: string) {

        // call API to authenticate user
        this.name = username;
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };

        this.http.post('https://localhost:8443/jeopardy/rest/login', '{"username":"' + username + '", "password":"' + password + '"}',
            httpOptions).subscribe((data: Login) => {this.loginData = data; this.router.navigate(['/choose']); },
                (err: HttpErrorResponse) => {console.log(err); this.wrongCredentials = true; } );

    }

    logout() {

        // call API to logout user

        // remove the localstorage token
        localStorage.removeItem('access_token');
        this.loggedin = false;
    }


  isAuthenticated(): Boolean {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
    // get the auth token from localStorage
    this.http.post('https://localhost:8443/jeopardy/rest/validateToken', '{"authToken":"' + this.loginData.authToken + '"}',
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
