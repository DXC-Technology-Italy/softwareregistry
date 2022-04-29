import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AuthenticationService} from 'src/app/service/auth/authentication.service';

@Component({
  selector: 'app-login-view',
  templateUrl: './login-view.component.html',
  styleUrls: ['./login-view.component.css']
})
export class LoginViewComponent implements OnInit {

  username: string;
  password: string;
  invalidLogin: boolean;
  error: string;

  constructor(private router: Router, private loginService: AuthenticationService) {

    this.username = '';
    this.password = '';
    this.invalidLogin = false;
    this.error = '';
    if (this.loginService.isUserLoggedIn()) {
      this.router.navigate(['']);
    }

  }

  login($event: any): void {
    (this.loginService.authenticate(this.username, this.password).subscribe(
        data => {
          if (data.authenticated === true) {
            this.invalidLogin = false;
            this.error = '';
            this.router.navigate(['/']);

          } else {
            this.invalidLogin = true;
            this.error = 'Si è verificato un errore durante il login';
          }
        },
        error => {
          this.invalidLogin = true;
          this.error = 'Username o password errati';
        }
      )
    );
  }

  ngOnInit(): void {
  }

}
