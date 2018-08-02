import {Injectable} from '@angular/core';
import {HttpHeaders, HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Router} from '@angular/router';

const CURRENT_USER_KEY = 'currentUser';

@Injectable()
export class AuthService {

  loggedIn = false;
  wrongCredentials = false;
  currentUser: User;

  constructor(private http: HttpClient, private router: Router) {
  }

  authenticateUser(username: string, password: string) {

    // call API to authenticate user
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    this.http.post('rest/login', '{"username":"' + username + '", "password":"' + password + '"}',
      httpOptions).subscribe((data: Login) => {
        this.loggedIn = true;
        this.currentUser = new User(data.userId, username, data.authToken);
        localStorage.setItem(CURRENT_USER_KEY, JSON.stringify(this.currentUser));
        this.router.navigate(['/choose']);
      },
      (err: HttpErrorResponse) => {
        console.log(err);
        this.wrongCredentials = true;
      });

  }

  logout() {
    // remove localStorage session data
    localStorage.removeItem(CURRENT_USER_KEY);
    this.currentUser = null;
    this.loggedIn = false;
    this.router.navigate(['/login']);
  }


  isAuthenticated(): Boolean {
    if (!this.currentUser) {
      // maybe page reload, try to fetch from localStorage
      this.currentUser = JSON.parse(localStorage.getItem(CURRENT_USER_KEY));
      if (this.currentUser) {
        // session restored
        this.loggedIn = true;
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
    this.http.post('rest/validateUser', JSON.stringify(this.currentUser),
      httpOptions).subscribe((data: Validator) => {
        if (data.valid === false) {
          this.logout();
        }
      },
      (err: HttpErrorResponse) => {
        console.log(err);
        this.logout();
      });
    return this.loggedIn;
  }
}

interface Login {
  authToken: string;
  userId: number;
}

interface Validator {
  valid: Boolean;
}

export class User {
  id: number;
  username: string;
  authToken: string;

  constructor(id, name, authToken) {
    this.id = id;
    this.username = name;
    this.authToken = authToken;
  }

}
