import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';
import {Observable} from 'rxjs';

export class User {
  constructor(public status: string) {}
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  constructor(private httpClient: HttpClient, private router: Router) {}
// Provide username and password for authentication, and once authentication is successful,
// store JWT token in session
  authenticate(username: string, password: string): Observable<any> {
      const httpOptions = {
        headers: new HttpHeaders({
          Authorization: 'Basic ' + btoa( username + ':' + password )
        })
      };
      return this.httpClient
        .post<any>(environment.apiUrl + '/login', {}, httpOptions)
        .pipe(
          map(userData => {
            if (userData.authenticated === true) {
              const date: Date = new Date();
              const tokenStr = 'Basic ' + window.btoa(username + ':' + password);
              localStorage.setItem('token', tokenStr);
              localStorage.setItem('username', username);
              localStorage.setItem('role', userData.role);
              localStorage.setItem('theme', userData.theme);
              localStorage.setItem('loginDate', date.getTime().toString());
            }
            return userData;
          })
        );
  }

  httpOptions(): {} {
    let httpOptions = {};
    if (this.isUserLoggedIn()) {
      httpOptions = {
        headers: new HttpHeaders({
          Authorization: localStorage.getItem('token') || ''
        })
      };
    }
    return httpOptions;

  }
  isUserLoggedIn(): boolean {
    const token = localStorage.getItem('token');
    if (token === null) {
      return false;
    }

    const loginDate: string = localStorage.getItem('loginDate') || '0';
    const now: Date = new Date();
    // tslint:disable-next-line:radix
    const diffMs = (now.getTime() - parseInt(loginDate)); // milliseconds
    // let diffMins = Math.round(((diffMs % 86400000) % 3600000) / 60000);
    const diffDays = diffMs / 1000 / 60 / 60 / 24;

    if (diffDays >= 1) {
        this.logOut();
        this.router.navigate(['/login']);
    }
    return true;
  }

  getUsername(): string {
    return localStorage.getItem('username') || '';
  }

  getRole(): string {
    return localStorage.getItem('role') || '';
  }

  selectedTheme(): string {
    return localStorage.getItem('theme') || 'light';
  }
  saveSelectedTheme(): void {
    const username = localStorage.getItem('username');
    const theme = localStorage.getItem('theme');

    this.httpClient
      .post<any>(environment.apiUrl + '/user/updateTheme?username=' + username + '&theme=' + theme, this.httpOptions()).subscribe(
        data => {
          console.log(data);
          return data;
        }
      );
  }

  logOut(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('theme');
    localStorage.removeItem('username');
    localStorage.removeItem('role');
  }
}
