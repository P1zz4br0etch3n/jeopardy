import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpClient, HttpErrorResponse } from '@angular/common/http';



@Injectable()
export class AuthService {

    loggedin = false;
    loginData: Login;

    constructor(private http: HttpClient) { }

    authenticateUser(username: string, password: string) {

        // call API to authenticate user

        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };

        this.http.post('jeopardy/rest/login', '{"username":"' + username + '", "password":"' + password + '"}',
            httpOptions).subscribe((data: Login) => this.loginData = data,
                (err: HttpErrorResponse) => console.log(err));

    }

    logout() {

        // call API to logout user

        // remove the localstorage token
        localStorage.removeItem('access_token');
        this.loggedin = false;
    }



    isAuthenticated() {
        const httpOptions = {
            headers: new HttpHeaders({
                'Content-Type': 'application/json'
            })
        };
        // get the auth token from localStorage
        this.http.post('/jeopardy/rest/validateToken', '{"authToken":"' + this.loginData.authToken + '"}',
            httpOptions).subscribe((data: Validator) => {
                if (data.valid === true) {
                    this.loggedin = true;
                    return true;
                } else {
                    return false;
                }
            },
                (err: HttpErrorResponse) => console.log(err));
    }


}
interface Login {
    authToken: string;
    userId: number;
}
interface Validator {
    valid: Boolean;
}
