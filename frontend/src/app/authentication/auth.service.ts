import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';



@Injectable()
export class AuthService {

    loggedin = false;
    constructor() {}

    authenticateUser(username: string, password: string) {

       // call API to authenticate user

       // set the token in localstorage
       if (username === 'admin' && password === 'admin') {
        localStorage.setItem('access_token', 'jwt_token');
       }

    }

    logout() {

      // call API to logout user

      // remove the localstorage token
      localStorage.removeItem('access_token');
      this.loggedin = false;
    }



    isAuthenticated() {
        // get the auth token from localStorage
        const token = localStorage.getItem('access_token');

        // check if token is set, then...
        if (token) {
            this.loggedin = true;
            return true;
        }

        return false;
    }


}
