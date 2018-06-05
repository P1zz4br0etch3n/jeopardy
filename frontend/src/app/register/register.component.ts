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
  emptyInput = false;

  clickRegister() {
    console.log(this.user);
    if (this.user === (undefined || '') || this.pwd === (undefined || '') || this.pwdWdh === (undefined || '')) {
      this.emptyInput = true;
    } else {
      if (this.pwd === this.pwdWdh) {
        this.equalPwd = true;
        this.router.navigate(['/login']);
      } else {
        this.equalPwd = false;
      }
    }
  }

}
