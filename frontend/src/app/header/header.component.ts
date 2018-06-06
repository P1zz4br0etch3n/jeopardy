import { Router } from '@angular/router';
import { AuthService } from './../authentication/auth.service';
import { Component } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import {MatIconRegistry} from '@angular/material';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(iconRegistry: MatIconRegistry, sanitizer: DomSanitizer, public authService: AuthService, private router: Router) {
    iconRegistry.addSvgIcon(
      'usercircle',
      sanitizer.bypassSecurityTrustResourceUrl('../assets/usericon.svg'));
  }
  username = 'admin';

  logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

}
