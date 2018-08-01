import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from '../../../node_modules/rxjs/Observable';
import { RequestOptions, Request, RequestMethod } from '@angular/http';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(public router: Router, public http: HttpClient) { }
  @Input() user: string;
  @Input() pwd: string;
  @Input() pwdWdh: string;
  equalPwd = true;
  emptyInput = false;

  clickRegister() {
    console.log(this.user);
    if (this.user === undefined || this.pwd === undefined || this.pwdWdh === undefined ||
      this.user === '' || this.pwd === '' || this.pwdWdh === '') {
      this.emptyInput = true;
    } else {
      if (this.pwd === this.pwdWdh) {
        this.equalPwd = true;
        this.sendRegistration(this.user, this.pwd);
        this.router.navigate(['/login']);
      } else {
        this.equalPwd = false;
      }
    }
  }

  sendRegistration(user: string, pwd: string) {
    const userdata = {
      username: user,
      password: pwd
    };
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };

    this.http.post('rest/users', userdata, httpOptions).subscribe({ error: e => console.error(e) });

  }
}
