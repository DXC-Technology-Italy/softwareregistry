import { Component } from '@angular/core';
import { AuthenticationService } from './service/auth/authentication.service';
import {environment} from 'src/environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Java Deps';
  VERSION = '2.3';
  settingsDialogVisibility = 'none';
  themes = [{ name: 'Chiaro', value: 'light' }, { name: 'Scuro', value: 'dark' } ];
  
  selectedTheme = '';
  gitlabUrl  = environment.gitlabUrl
  jenkinsUrl = environment.jenkinsUrl
  sonarUrl   = environment.sonarUrl
  nexusUrl   = environment.nexusUrl

  constructor(private loginService: AuthenticationService) {
    this.selectedTheme = localStorage.getItem('theme') || 'light';

  }
  getSelectedTheme(): string {
    this.selectedTheme = this.loginService.selectedTheme();
    return this.selectedTheme;
  }

  getRole(): string {
    return this.loginService.getRole() || '';
  }

  getUsername(): string {
    return this.loginService.getUsername() || '';
  }

  selectTheme($event: any): void {
    this.selectedTheme = $event.target.value;
    localStorage.setItem('theme', this.selectedTheme);
    this.loginService.saveSelectedTheme();
  }
  showSettingsDialog($event: any): void {
    this.settingsDialogVisibility = 'block';
  }

  hideSettingsDialog($event: any): void {
    this.settingsDialogVisibility = 'none';
  }

  logout($event: any): void {
    this.loginService.logOut();
  }
  isUserLoggedIn(): boolean {
    return this.loginService.isUserLoggedIn();
  }
  isAdmin(): boolean{
      return this.getRole() === 'ADMIN';
  }
}
