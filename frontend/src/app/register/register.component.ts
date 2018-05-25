import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  constructor(public router: Router) {}
  @Input() user: string;
  @Input() pwd: string;
  @Input() pwdWdh: string;
  equalPwd = true;

  clickRegister() {
    if (this.pwd === this.pwdWdh) {
      this.equalPwd = true;
      this.router.navigate(['/login']);
    } else {
      this.equalPwd = false;
    }
  }

}
